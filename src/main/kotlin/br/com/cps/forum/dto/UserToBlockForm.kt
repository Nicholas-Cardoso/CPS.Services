package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Reason
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserToBlockForm(
    @field:NotBlank(message = "Email user is required.")
    @field:Email(message = "Email user must be a valid email address.")
    val userEmail: String,
    @field: NotBlank(message = "BlockByReason is required.")
    val blockByReason: Reason,
    @field: NotBlank(message = "Reason is required.")
    val messageReason: String,
    @field: NotBlank(message = "AdminEmail is required.")
    @field:Email(message = "AdminEmail must be a valid email address.")
    val adminEmail: String
)