package br.com.cps.forum.dto

import org.jetbrains.annotations.NotNull

data class UserEmailForm(
    @field:NotNull("Email is required.")
    val email: String
)
