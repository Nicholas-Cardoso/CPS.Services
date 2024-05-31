package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Privacy
import br.com.cps.forum.model.Usuario
import java.io.Serializable
import java.time.LocalDateTime

data class TopicosView(
    val id: Long?,
    val title: String,
    val privacy: Privacy,
    val section: String,
    val body: String,
    val tag: String,
    val answer: List<AnswersView>,
    val user: Usuario,
    val created_at: LocalDateTime,
    var updated_at: LocalDateTime
) : Serializable
