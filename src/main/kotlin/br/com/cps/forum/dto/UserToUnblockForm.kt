package br.com.cps.forum.dto

import jakarta.validation.constraints.NotBlank

data class UserToUnblockForm(
    @field:NotBlank(message = "Email is required.")
    val userEmail: String,
    @field:NotBlank(message = "Admin Email is required.")
    val adminEmail: String,
)
