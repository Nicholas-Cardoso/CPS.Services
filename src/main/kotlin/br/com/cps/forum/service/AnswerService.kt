package br.com.cps.forum.service

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.dto.AnswerUpdateForm
import br.com.cps.forum.dto.AnswerView
import br.com.cps.forum.dto.AnswersView
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.extension.getTopicoById
import br.com.cps.forum.extension.getUserById
import br.com.cps.forum.mapper.AnswerFormMapper
import br.com.cps.forum.mapper.AnswerViewMapper
import br.com.cps.forum.model.Answers
import br.com.cps.forum.repository.AnswerRepository
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.repository.UsuarioRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

private const val notFound: String = "Resposta não encontrada."
private const val topicoIdIsDifferent: String = "O topicoId não pode ser diferente do topicoId do parent."

@Service
class AnswerService(
    private val repository: AnswerRepository,
    private val userRepository: UsuarioRepository,
    private val topicoRepository: TopicoRepository,
    private val mapperToView: AnswerViewMapper,
    private val mapperToForm: AnswerFormMapper
) {
//    @Cacheable(cacheNames = ["Answer"])
    fun listAnswers(topicoId: Long?, page: Pageable): Page<AnswersView> {
        val answers = if (topicoId != null) {
            repository.findByTopicoId(topicoId, page)
        } else {
            repository.findAll(page)
        }

        val resultList = answers.map { answer ->
            val processedIds = mutableSetOf<Long>()
            val answerView = mapperToView.map(answer)
            val topicoIdCorrected = topicoId ?: answer.topico.id
            answerView.copy(topicoId = topicoIdCorrected, answerChild = getChildrenAnswers(answer.id, processedIds))
        }.toList()

        return PageImpl(resultList, page, answers.totalElements)
    }

//    @Cacheable(cacheNames = ["Answer"], key = "#id")
    fun getById(id: Long): AnswerView {
        val answer = findByAnswer(id)
        return mapperToView.mapToAnswer(answer)
    }

//    @CacheEvict(cacheNames = ["Answer", "Topicos"], allEntries = true)
    @Throws(NotFoundException::class)
    fun createAnswer(answerForm: AnswerForm, parentId: Long?): AnswerView {
        when {
            parentId != null -> {
                val parentAnswer = findByAnswer(parentId)
                if (parentAnswer.topico.id!! != answerForm.topicoId) {
                    throw NotFoundException(topicoIdIsDifferent)
                }
                val answers: Answers = Answers(
                    topico = answerForm.getTopicoById(topicoRepository, answerForm.topicoId),
                    user = answerForm.getUserById(userRepository, answerForm.userId),
                    answerBody = answerForm.answerBody,
                    answerFather = parentAnswer
                )

                val savedAnswer = repository.save(answers)
                return mapperToView.mapToAnswer(savedAnswer)
            }

            else -> {
                val answer = mapperToForm.map(answerForm)
                val answerCreated = repository.save(answer)
                return mapperToView.mapToAnswer(answerCreated)
            }
        }
    }

//    @CachePut(cacheNames = ["Answer", "Topicos"], key = "#answerUpdate.id")
    fun updateAnswer(answerUpdate: AnswerUpdateForm): AnswerView {
        val existingAnswer = findByAnswer(answerUpdate.id)
        answerUpdate.let {
            existingAnswer.answerBody = it.answerBody
        }

        repository.save(existingAnswer)
        return mapperToView.mapToAnswer(existingAnswer)
    }

//    @CacheEvict(cacheNames = ["Answer", "Topicos"], allEntries = true)
    fun deleteAnswer(id: Long) {
        return repository.deleteById(id)
    }

    private fun getChildrenAnswers(parentId: Long?, processedIds: MutableSet<Long>): List<AnswersView> {
        val children = repository.findByAnswerFatherId(parentId)
        val result = mutableListOf<AnswersView>()

        for (child in children) {
            if (!processedIds.contains(child.id)) {
                processedIds.add(child.id!!)

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
