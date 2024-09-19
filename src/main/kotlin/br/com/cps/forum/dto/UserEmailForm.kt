package br.com.cps.forum.dto

import jakarta.validation.constraints.NotBlank

data class UserEmailForm(
    @field:NotBlank(message = "Email is required.")
    val email: String
)
