package br.com.cps.forum.dto

import br.com.cps.forum.model.Role

data class UserView(
    val id: Long?,
    val idGraph: Long?,
    val email: String,
    var firstName: String,
    var lastName: String,
    val isBlockedUser: Boolean,
    val role: Role
)
