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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

private const val notFound: String = "Resposta não encontrada."

@Service
class AnswerService(
    private val repository: AnswerRepository,
    private val userRepository: UsuarioRepository,
    private val topicoRepository: TopicoRepository,
    private val mapperToView: AnswerViewMapper,
    private val mapperToForm: AnswerFormMapper
) {
    fun listAnswers(topicoId: Long?, page: Pageable): Page<AnswersView> {
        val answers = if (topicoId != null) {
            repository.findByTopicoId(topicoId, page)
        } else {
            repository.findAll(page)
        }

        return answers.map {
            val answerView = mapperToView.map(it)
            answerView.copy(answerChild = getChildrenAnswers(it.id))
        }
    }

    fun getById(id: Long): AnswerView {
        val answer = findByAnswer(id)
        return mapperToView.mapToAnswer(answer)
    }

    fun createAnswer(answerForm: AnswerForm, parentId: Long?): AnswerView {
        when {
            parentId != null -> {
                val parentAnswer = findByAnswer(parentId)
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

    fun updateAnswer(answerUpdate: AnswerUpdateForm): AnswerView {
        val existingAnswer = findByAnswer(answerUpdate.id)
        answerUpdate.let {
            existingAnswer.answerBody = it.answerBody
        }

        repository.save(existingAnswer)
        return mapperToView.mapToAnswer(existingAnswer)
    }

    fun deleteAnswer(id: Long) {
        return repository.deleteById(id)
    }

    private fun findByAnswer(id: Long): Answers {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException(notFound)
            }
    }

    private fun getChildrenAnswers(parentId: Long?): List<AnswersView> {
        val children = repository.findByAnswerFatherId(parentId)
        return children.map {
            val answerChild = getChildrenAnswers(it.id)
            AnswersView(
                id = it.id,
                answerBody = it.answerBody,
                topicoId = it.topico.id,
                userId = it.user.id,
                answerChild = answerChild
            )
        }
    }
}
