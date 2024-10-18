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

fun transformTitleToSlug(title: String): String {
    return title
        .lowercase()
        .replace(Regex("[áàãâä]"), "a")
        .replace(Regex("[éèêë]"), "e")
        .replace(Regex("[íìîï]"), "i")
        .replace(Regex("[óòõôö]"), "o")
        .replace(Regex("[úùûü]"), "u")
        .replace(Regex("[ç]"), "c")
        .replace(Regex("[^a-z0-9\\s]"), "")
        .replace("\\s+".toRegex(), "-")
        .trim('-')
}