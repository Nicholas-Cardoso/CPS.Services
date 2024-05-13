package br.com.cps.forum.dto

import java.io.Serializable

data class AnswersView(
    val id: Long?,
    val answerBody: String,
    val topicoId: Long?,
    val userId: Long?,
    val answerChild: List<AnswersView> = mutableListOf()
) : Serializable