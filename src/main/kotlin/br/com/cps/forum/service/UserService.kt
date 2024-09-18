package br.com.cps.forum.service

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.dto.UserToBlockForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.mapper.UserViewMapper
import br.com.cps.forum.model.User
import br.com.cps.forum.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

private const val message: String = "User not found."

@Service
class UserService(
    private val repository: UserRepository,
    private val userToView: UserViewMapper
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = repository.findByEmail(username) ?: throw RuntimeException()
        return UserDetail(user)
    }

    fun getUserByEmail(user: UserEmailForm): UserView? {
        return repository.findByEmail(user.email)?.let {
            userToView.map(it)
        }
    }

    fun getById(id: Long): User {
        return repository.findById(id).orElseThrow {
            NotFoundException(message)
        }
    }

    fun blockUser(userToBlock: UserToBlockForm): UserView {
        val user = repository.findByEmail(userToBlock.userEmail)?.copy(isBlockedUser = true)


    }
}