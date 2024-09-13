package br.com.cps.forum.dto

import br.com.cps.forum.model.Answers
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.model.User
import java.io.Serializable

data class AnswerView(
    val id: Long?,
    val answerBody: String,
    val topico: Topicos,
    val user: User,
    val answerChild: MutableList<Answers> = mutableListOf()
) : Serializable