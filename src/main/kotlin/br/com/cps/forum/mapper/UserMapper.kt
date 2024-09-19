package br.com.cps.forum.mapper

import br.com.cps.forum.dto.UserView
import br.com.cps.forum.extension.getRoleNameByUserId
import br.com.cps.forum.model.User
import br.com.cps.forum.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val userRepository: UserRepository
) : Mapper<User, UserView> {

    override fun map(t: User): UserView {
        return UserView(
            idGraph = t.idGraph,
            email = t.email,
            firstName = t.firstName,
            lastName = t.lastName,
            isBlockedUser = t.isBlockedUser,
            blockByReason = t.blockByReason,
            blockedBy = t.blockedBy,
            unblockedBy = t.unblockedBy,
            role = t.getRoleNameByUserId(userRepository, t.id)
        )
    }
}