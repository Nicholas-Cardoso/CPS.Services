package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Reason
import jakarta.validation.constraints.NotBlank

data class UserToBlockForm(
    @field:NotBlank(message = "Email user is required.")
    val userEmail: String,
    @field: NotBlank(message = "BlockByReason is required.")
    val blockByReason: Reason,
    @field: NotBlank(message = "Reason is required.")
    val messageReason: String,
    @field: NotBlank(message = "AdminEmail is required.")
    val adminEmail: String
)