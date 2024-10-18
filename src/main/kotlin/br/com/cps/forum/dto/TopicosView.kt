package br.com.cps.forum.dto

import br.com.cps.forum.model.enum.Privacy
import br.com.cps.forum.model.User
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZonedDateTime

data class TopicosView(
    val id: Long?,
    val title: String,
    val slug: String,
    val privacy: Privacy,
    val section: String,
    val body: String,
    val tag: String,
    val answer: List<AnswersView>,
    val user: User,
    val createdAt: ZonedDateTime,
    var updatedAt: ZonedDateTime
) : Serializable
