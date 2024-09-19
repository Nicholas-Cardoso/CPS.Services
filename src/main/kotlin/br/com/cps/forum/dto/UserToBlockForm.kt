package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Reason
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserToBlockForm(
    @field:NotBlank(message = "Email User is required.")
    @field:Email(message = "Email user must be a valid email address.")
    val userEmail: String,
    @field: NotNull(message = "BlockByReason is required.")
    val blockByReason: Reason,
    @field: NotBlank(message = "Email Admin is required.")
    @field:Email(message = "Email Admin must be a valid email address.")
    val adminEmail: String
)