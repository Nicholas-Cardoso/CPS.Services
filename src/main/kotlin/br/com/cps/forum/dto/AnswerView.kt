package br.com.cps.forum.dto

import br.com.cps.forum.model.Topicos
import br.com.cps.forum.model.Usuario

data class AnswerView(
    val id: Long?,
    val answerBody: String,
    val topico: Topicos,
    val user: Usuario,
    val answerChild: List<AnswersView> = mutableListOf()
)