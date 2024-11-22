package br.com.cps.forum.service

import br.com.cps.forum.dto.AnswersView
import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.dto.VotesForm
import br.com.cps.forum.mapper.AnswerViewMapper
import br.com.cps.forum.mapper.TopicosViewMapper
import br.com.cps.forum.model.enum.PostType
import br.com.cps.forum.model.enum.VoteType
import br.com.cps.forum.repository.AnswerRepository
import br.com.cps.forum.repository.TopicoRepository
import org.springframework.stereotype.Service

@Service
class VotesService(
    private val topicosRepository: TopicoRepository,
    private val answerRepository: AnswerRepository,
    private val mapperTopicosToView: TopicosViewMapper,
    private val mapperAnswersToView: AnswerViewMapper
) {

    fun countingVotesToTopicsOrAnswers(modalVotes: VotesForm) {
        when (modalVotes.postType) {
            PostType.TOPICO -> updateTopicoVotes(modalVotes)
            PostType.ANSWER -> updateAnswerVotes(modalVotes)
            else -> throw IllegalArgumentException("Tipo de post desconhecido.")
        }
    }

    private fun updateTopicoVotes(modalVotes: VotesForm): TopicosView {
        val findTopico = topicosRepository.findById(modalVotes.topicoId!!)
            .orElseThrow { IllegalArgumentException("Tópico não encontrado.") }
        when (modalVotes.voteType) {
            VoteType.POSITIVE -> findTopico.positiveVotes++
            VoteType.NEGATIVE -> findTopico.negativeVotes++
        }

        topicosRepository.save(findTopico)
        return mapperTopicosToView.map(findTopico)
    }

    private fun updateAnswerVotes(modalVotes: VotesForm): AnswersView {
        val findAnswer = answerRepository.findById(modalVotes.answerId!!)
            .orElseThrow { IllegalArgumentException("Answer não encontrado.") }
        when (modalVotes.voteType) {
            VoteType.POSITIVE -> findAnswer.positiveVotes++
            VoteType.NEGATIVE -> findAnswer.negativeVotes++
        }

        answerRepository.save(findAnswer)
        return mapperAnswersToView.map(findAnswer)
    }
}
