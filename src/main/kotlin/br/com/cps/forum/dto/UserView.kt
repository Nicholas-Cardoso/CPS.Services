package br.com.cps.forum.dto

data class UserView(
    val idGraph: Long?,
    val email: String,
    var firstName: String,
    var lastName: String,
    val isBlockedUser: Boolean,
    val reasonBlock: String,
    val blockedBy: String,
    val role: List<String>?
)
