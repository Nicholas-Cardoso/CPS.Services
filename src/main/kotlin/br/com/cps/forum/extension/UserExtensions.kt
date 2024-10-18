package br.com.cps.forum.extension

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.exception.NotFoundException
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

fun authEmails(userEmail: String, adminEmail: String, repository: UserRepository): Boolean {
    val userExists = repository.findByEmail(userEmail) != null
    val adminExists = repository.findByEmail(adminEmail) != null

    return userExists && adminExists
}

fun transformNameToSlug(firstName: String, lastName: String): String {
    val fullName = "$firstName $lastName"

    return fullName
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