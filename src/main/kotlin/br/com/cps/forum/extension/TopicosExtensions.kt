package br.com.cps.forum.extension

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.repository.TopicoRepository

private const val notFound: String = "Tópico não encontrado."

fun AnswerForm.getTopicoById(topicosRepository: TopicoRepository, id: Long): Topicos {
    return topicosRepository.findById(id)
        .orElseThrow {
            NotFoundException(notFound)
        }
}