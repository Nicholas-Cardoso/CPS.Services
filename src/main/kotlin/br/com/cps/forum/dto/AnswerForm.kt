package br.com.cps.forum.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AnswerForm(
    @field:NotNull(message = "O topicoId é obrigatório.")
    val topicoId: Long,
    @field:NotNull(message = "O userId é obrigatório.")
    val userId: Long,
    @field:NotBlank
    val answerBody: String,
)