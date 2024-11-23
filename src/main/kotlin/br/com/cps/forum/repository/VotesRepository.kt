package br.com.cps.forum.repository

import br.com.cps.forum.model.Votes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VotesRepository : JpaRepository<Votes, Long> {
    fun existsByUser_IdAndTopicos_Id(userId: Long, topicId: Long): Boolean
    fun existsByUser_IdAndAnswers_Id(userId: Long, answerId: Long): Boolean

    fun findByUser_IdAndTopicos_Id(userId: Long, topicoId: Long): Votes?
    fun findByUser_IdAndAnswers_Id(userId: Long, answerId: Long): Votes?

    @Query("SELECT SUM(v.positiveVotes) - SUM(v.negativeVotes) FROM Votes v WHERE v.topicos.id = :topicoId")
    fun countVotesForTopicos(@Param("topicoId") topicoId: Long): Int?

    @Query("SELECT SUM(v.positiveVotes) - SUM(v.negativeVotes) FROM Votes v WHERE v.answers.id = :answerId")
    fun countVotesForAnswers(@Param("answerId") answerId: Long): Int?

    @Query(
        """
        SELECT SUM(CASE 
                    WHEN v.topicos.id IS NOT NULL THEN v.positiveVotes - v.negativeVotes 
                    WHEN v.answers.id IS NOT NULL THEN v.positiveVotes - v.negativeVotes 
                    ELSE 0 
                END) 
        FROM Votes v 
        WHERE v.user.id = :userId
    """
    )
    fun countTotalVotesForUser(@Param("userId") userId: Long): Int?
}
