package br.com.cps.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AnswerUpdateForm(
    @field:NotNull
    val id: Long,
    @field:NotBlank
    val answerBody: String
)