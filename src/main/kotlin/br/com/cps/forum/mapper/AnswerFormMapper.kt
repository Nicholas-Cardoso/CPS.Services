package br.com.cps.forum.mapper

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.extension.getTopicoById
import br.com.cps.forum.extension.getUserById
import br.com.cps.forum.model.Answers
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class AnswerFormMapper(
    private val topicoRepository: TopicoRepository,
    private val userRepository: UserRepository
) : Mapper<AnswerForm, Answers> {
    override fun map(t: AnswerForm): Answers {
        return Answers(
            topico = t.getTopicoById(topicoRepository, t.topicoId),
            user = t.getUserById(userRepository, t.userId),
            answerBody = t.answerBody
        )
    }
}
