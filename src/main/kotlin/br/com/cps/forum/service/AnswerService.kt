package br.com.cps.forum.service

import br.com.cps.forum.dto.*
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.extension.checkAndSaveBrainAI
import br.com.cps.forum.extension.getTopicoById
import br.com.cps.forum.extension.getUserById
import br.com.cps.forum.mapper.AnswerFormMapper
import br.com.cps.forum.mapper.AnswerViewMapper
import br.com.cps.forum.model.Answers
import br.com.cps.forum.model.Role
import br.com.cps.forum.network.api.HashBrainService
import br.com.cps.forum.repository.AnswerRepository
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

private const val notFound: String = "Resposta não encontrada."
private const val topicoIdIsDifferent: String = "O topicoId não pode ser diferente do topicoId do parent."

@Service
class AnswerService(
    private val repository: AnswerRepository,
    private val userRepository: UserRepository,
    private val topicoRepository: TopicoRepository,
    private val mapperToView: AnswerViewMapper,
    private val mapperToForm: AnswerFormMapper,
    private val brainService: HashBrainService
) {
    fun listAnswers(topicoId: Long?, page: Pageable = PageRequest.of(0, 10)): Page<AnswersView> {
        val answers = if (topicoId != null) {
            repository.findByTopicoId(topicoId, page)
        } else {
            repository.findAll(page)
        }

        val processedIds = mutableSetOf<Long>()
        val resultList = answers.map { answer ->
            val answerView = mapperToView.map(answer)
            val topicoIdCorrected = topicoId ?: answer.topico.id
            answerView.copy(topicoId = topicoIdCorrected, answerChild = getChildrenAnswers(answer.id, processedIds))
        }.filter { processedIds.add(it.id!!) }.toList()

        return PageImpl(resultList, page, answers.totalElements)
    }

    fun getById(id: Long): AnswerView {
        val answer = findByAnswer(id)
        return mapperToView.mapToAnswer(answer)
    }

    @Throws(NotFoundException::class)
    fun createAnswer(answerForm: AnswerForm, parentId: Long?): AnswerView {
        val authentication = SecurityContextHolder.getContext().authentication.authorities.first()
        val userId = (authentication as Role).id

        when {
            parentId != null -> {
                val parentAnswer = findByAnswer(parentId)
                if (parentAnswer.topico.id!! != answerForm.topicoId) {
                    throw NotFoundException(topicoIdIsDifferent)
                }
                val answers = Answers(
                    topico = answerForm.getTopicoById(topicoRepository, answerForm.topicoId),
                    user = answerForm.getUserById(userRepository, userId),
                    answerBody = answerForm.answerBody,
                    answerFather = parentAnswer
                )

                val savedAnswer =
                    checkAndSaveBrainAI(brainService, answers, repository, bodyExtractor = { it.answerBody })

                return mapperToView.mapToAnswer(savedAnswer)
            }

            else -> {
                val answer = mapperToForm.map(answerForm, userId)
                val answerCreated =
                    checkAndSaveBrainAI(brainService, answer, repository, bodyExtractor = { it.answerBody })
                return mapperToView.mapToAnswer(answerCreated)
            }
        }
    }

    fun updateAnswer(answerUpdate: AnswerUpdateForm): AnswerView {
        val existingAnswer = findByAnswer(answerUpdate.id)
        answerUpdate.let {
            existingAnswer.answerBody = it.answerBody
        }

        checkAndSaveBrainAI(brainService, existingAnswer, repository, bodyExtractor = { it.answerBody })

        return mapperToView.mapToAnswer(existingAnswer)
    }

    fun deleteAnswer(id: Long) {
        val findId = findByAnswer(id)
        return repository.deleteById(findId.id!!)
    }

    private fun getChildrenAnswers(parentId: Long?, processedIds: MutableSet<Long>): List<AnswersView> {
        val children = repository.findByAnswerFatherId(parentId)
        val result = mutableListOf<AnswersView>()

        for (child in children) {
            if (processedIds.add(child.id!!)) {
                val answerChild = getChildrenAnswers(child.id, processedIds)
                result.add(
                    AnswersView(
                        id = child.id,
                        answerBody = child.answerBody,
                        topicoId = child.topico.id,
                        userId = child.user.id,
                        answerChild = answerChild
                    )
                )
            }
        }
        return result
    }

    private fun findByAnswer(id: Long): Answers {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException(notFound)
            }
    }
}
