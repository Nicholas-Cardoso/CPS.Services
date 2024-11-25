package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Reason

data class UserView(
    val id: Long,
    val oId: String?,
    val email: String?,
    var name: String?,
    var slug: String?,
    val isBlockedUser: Boolean?,
    val blockByReason: Reason? = null,
    val blockedBy: String? = null,
    val unblockedBy: String? = null,
    val role: List<String>?,
    val totalVotes: Int? = 0
)
