package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Reason

data class UserView(
    val idGraph: Long?,
    val email: String,
    var firstName: String,
    var lastName: String,
    val isBlockedUser: Boolean,
    val blockByReason: Reason? = null,
    val blockedBy: String? = null,
    val unblockedBy: String? = null,
    val role: List<String>?
)
