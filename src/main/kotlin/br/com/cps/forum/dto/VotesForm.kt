package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.VoteType
import jakarta.validation.constraints.NotNull

data class VotesForm(
    @field:NotNull(message = "O tipo do voto não pode ser nulo.")
    val voteType: VoteType,
    @field:NotNull(message = "O id do user não pode ser nulo.")
    val userId: Long
)
