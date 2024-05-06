package br.com.cps.forum.dto

import br.com.cps.forum.enum.Privacy
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TopicosForm(
    @field:NotNull
    val userId: Long,
    @field:NotBlank
    @field:Size(message = "O título deve ter no mínimo 15 caracteres e no máximo 150.", min = 15, max = 150)
    val title: String,
    val privacy: Privacy = Privacy.PUBLIC,
    @field:NotBlank(message = "Selecione uma seção.")
    val section: String,
    @field:NotBlank(message = "O corpo do tópico é obrigatório.")
    val body: String,
    val tag: String,
)
