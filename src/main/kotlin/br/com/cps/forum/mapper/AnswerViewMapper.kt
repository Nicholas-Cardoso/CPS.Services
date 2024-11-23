package br.com.cps.forum.mapper

import br.com.cps.forum.dto.AnswerView
import br.com.cps.forum.dto.AnswersView
import br.com.cps.forum.model.Answers
import br.com.cps.forum.repository.VotesRepository
import org.springframework.stereotype.Component

@Component
class AnswerViewMapper(
    private val votesRepository: VotesRepository
) : Mapper<Answers, AnswersView> {
    override fun map(t: Answers): AnswersView {
        return AnswersView(
            id = t.id,
            answerBody = t.answerBody,
            topicoId = t.topico.id,
            userId = t.user.id,
            votes = votesRepository.countVotesForAnswers(t.id!!)
        )
    }

    fun mapToAnswer(t: Answers): AnswerView {
        return AnswerView(
            id = t.id,
            answerBody = t.answerBody,
            topico = t.topico,
            user = t.user,
            answerChild = t.answerChild,
            votes = votesRepository.countVotesForAnswers(t.id!!)
        )
    }
}
