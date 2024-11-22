package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.PostType
import br.com.cps.forum.model.enum.VoteType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class VotesForm(
    @field:NotNull(message = "O postType é obrigatório.")
    val postType: PostType,
    val topicoId: Long? = null,
    val answerId: Long? = null,
    @field:NotBlank(message = "O tipo do voto não pode ser vazio.")
    val voteType: VoteType
)
