package br.com.cps.forum.mapper

import br.com.cps.forum.dto.UserView
import br.com.cps.forum.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper : Mapper<User, UserView> {

    override fun map(t: User): UserView {
        return UserView(
            oId = t.oId,
            email = t.email,
            name = t.name,
            slug = t.slug,
            isBlockedUser = t.isBlockedUser,
            blockByReason = t.blockByReason,
            blockedBy = t.blockedBy,
            unblockedBy = t.unblockedBy,
            role = t.role.map { it.getName() }
        )
    }
}