package br.com.cps.forum.service

import br.com.cps.forum.dto.UserEmailForm
import br.com.cps.forum.dto.UserToBlockForm
import br.com.cps.forum.dto.UserToUnblockForm
import br.com.cps.forum.dto.UserView
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.extension.authEmails
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
        val validationEmails = authEmails(userToBlock.userEmail, userToBlock.adminEmail, repository)
        if (!validationEmails)
            return "Error: Emails ${userToBlock.userEmail} or ${userToBlock.adminEmail} not found."

        val newUser = repository.findByEmail(userToBlock.userEmail)?.copy(
            isBlockedUser = true,
            blockByReason = userToBlock.blockByReason,
            blockedBy = userToBlock.adminEmail,
            unblockedBy = null
        )
        return repository.save(newUser!!).messageUserIsBlocked(true, userToBlock.userEmail)
    }

    fun unblockUser(userUnblock: UserToUnblockForm): String {
        val validationEmails = authEmails(userUnblock.userEmail, userUnblock.adminEmail, repository)
        if (!validationEmails)
            return "Error: Emails ${userUnblock.userEmail} or ${userUnblock.adminEmail} not found."

        val oldUser = repository.findByEmail(userUnblock.userEmail)?.copy(
            isBlockedUser = false,
            blockByReason = Reason.CLEAN,
            blockedBy = null,
            unblockedBy = userUnblock.adminEmail
        )
        return repository.save(oldUser!!).messageUserIsBlocked(false, userUnblock.userEmail)
    }
}