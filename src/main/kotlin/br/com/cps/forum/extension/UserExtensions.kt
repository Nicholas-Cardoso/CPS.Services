package br.com.cps.forum.extension

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.model.Usuario
import br.com.cps.forum.repository.UsuarioRepository

private const val notFound: String = "Usuário não encontrado."

fun AnswerForm.getUserById(userRepository: UsuarioRepository, id: Long): Usuario {
    return userRepository.findById(id)
        .orElseThrow {
            NotFoundException(notFound)
        }
}

fun TopicosForm.getUserById(userRepository: UsuarioRepository, id: Long): Usuario {
    return userRepository.findById(id)
        .orElseThrow {
            NotFoundException(notFound)
        }
}