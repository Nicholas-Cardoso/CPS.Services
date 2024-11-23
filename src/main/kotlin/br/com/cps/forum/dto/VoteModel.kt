package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.VoteType

data class VoteModel(
    val userId: Long,
    val voteType: VoteType,
)
