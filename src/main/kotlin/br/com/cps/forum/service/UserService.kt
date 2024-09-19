package br.com.cps.forum.service

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.dto.UserToBlockForm
import br.com.cps.forum.dto.UserToUnblockForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.mapper.UserMapper
import br.com.cps.forum.model.User
import br.com.cps.forum.model.enum.Reason
import br.com.cps.forum.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

private const val message: String = "User not found."

@Service
class UserService(
    private val repository: UserRepository,
    private val userMapper: UserMapper,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = repository.findByEmail(username) ?: throw RuntimeException()
        return UserDetail(user)
    }

    fun getUserByEmail(user: UserEmailForm): UserView? {
        return repository.findByEmail(user.email)?.let {
            userMapper.map(it)
        }
    }

    fun getById(id: Long): User {
        return repository.findById(id).orElseThrow {
            NotFoundException(message)
        }
    }

    fun blockUser(userToBlock: UserToBlockForm): String {
        val newUser = repository.findByEmail(userToBlock.userEmail)?.copy(isBlockedUser = true)?.apply {
            blockByReason = userToBlock.blockByReason
            unblockedBy = null
            blockedBy = userToBlock.adminEmail
        }

        return repository.save(newUser!!).messageUserIsBlocked(true, userToBlock.userEmail)
    }

    fun unblockUser(userUnblock: UserToUnblockForm): String {
        val oldUser = repository.findByEmail(userUnblock.userEmail)?.copy(isBlockedUser = false)?.apply {
            blockByReason = Reason.CLEAN
            blockedBy = null
            unblockedBy = userUnblock.adminEmail
        }

        return repository.save(oldUser!!).messageUserIsBlocked(false, userUnblock.userEmail)
    }
}