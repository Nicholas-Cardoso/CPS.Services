package br.com.cps.forum.service

import br.com.cps.forum.dto.VotesForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.model.Answers
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.model.User
import br.com.cps.forum.model.Votes
import br.com.cps.forum.model.enum.VoteType
import br.com.cps.forum.repository.AnswerRepository
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.repository.UserRepository
import br.com.cps.forum.repository.VotesRepository
import org.springframework.stereotype.Service

@Service
class VotesService(
    private val votesRepository: VotesRepository,
    private val topicosRepository: TopicoRepository,
    private val answerRepository: AnswerRepository,
    private val userRepository: UserRepository,
) {
    fun voteOnTopico(topicoId: Long, votesForm: VotesForm) {
        val topico = topicosRepository.findById(topicoId)
            .orElseThrow { NotFoundException("O tópico não existe.") }
        val user = userRepository.findById(votesForm.userId)
            .orElseThrow { NotFoundException("O usuário não existe.") }

        handleVote(
            user = user,
            topico = topico,
            answer = null,
            votesForm = votesForm
        )
    }

    fun voteOnAnswer(answerId: Long, votesForm: VotesForm) {
        val answer = answerRepository.findById(answerId)
            .orElseThrow { NotFoundException("A resposta não existe.") }
        val user = userRepository.findById(votesForm.userId)
            .orElseThrow { NotFoundException("O usuário não existe.") }

        handleVote(
            user = user,
            topico = null,
            answer = answer,
            votesForm = votesForm
        )
    }

    private fun handleVote(user: User, topico: Topicos?, answer: Answers?, votesForm: VotesForm) {
        val voteEntity = if (topico != null) {
            votesRepository.findByUser_IdAndTopicos_Id(user.id!!, topico.id!!)
        } else {
            votesRepository.findByUser_IdAndAnswers_Id(user.id!!, answer!!.id!!)
        }

        if (voteEntity != null) {
            updateVote(voteEntity, votesForm)
        } else {
            val newVote = createNewVote(user, topico, answer)
            updateVote(newVote, votesForm)
        }
    }

    private fun createNewVote(user: User, topico: Topicos?, answer: Answers?): Votes {
        val newVote = Votes(
            user = user,
            topicos = topico,
            answers = answer,
            positiveVotes = 0,
            negativeVotes = 0,
            hasVoted = true
        )
        return votesRepository.save(newVote)
    }

    private fun updateVote(voteEntity: Votes, votesForm: VotesForm) {
        when (votesForm.voteType) {
            VoteType.UPVOTE -> {
                if (voteEntity.positiveVotes == 1) {
                    voteEntity.positiveVotes--
                } else if (voteEntity.negativeVotes == 1) {
                    voteEntity.positiveVotes++
                    voteEntity.negativeVotes--
                } else {
                    voteEntity.positiveVotes++
                }
            }

            VoteType.DOWNVOTE -> {
                if (voteEntity.negativeVotes == 1) {
                    voteEntity.negativeVotes--
                } else if (voteEntity.positiveVotes == 1) {
                    voteEntity.negativeVotes++
                    voteEntity.positiveVotes--
                } else {
                    voteEntity.negativeVotes++
                }
            }
        }
        votesRepository.save(voteEntity)
    }
}
