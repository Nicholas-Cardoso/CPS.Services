package br.com.cps.forum.extension

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.model.Role
import br.com.cps.forum.model.User
import br.com.cps.forum.repository.UserRepository

private const val notFound: String = "Usuário não encontrado."

fun AnswerForm.getUserById(userRepository: UserRepository, id: Long): User {
    return userRepository.findById(id)
        .orElseThrow {
            NotFoundException(notFound)
        }
}

fun TopicosForm.getUserById(userRepository: UserRepository, id: Long): User {
    return userRepository.findById(id)
        .orElseThrow {
            NotFoundException(notFound)
        }
}

fun User.getRoleNameByUserId(userRepository: UserRepository, id: Long?): List<String>? {
    val user = userRepository.findUserById(id!!)
    return user?.role?.map { it.getName() }
}