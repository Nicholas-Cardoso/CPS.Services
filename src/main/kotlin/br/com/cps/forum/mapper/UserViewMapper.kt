package br.com.cps.forum.mapper

import br.com.cps.forum.dto.UserView
import br.com.cps.forum.model.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper : Mapper<User, UserView> {
    override fun map(t: User): UserView {
        return UserView(
            id = t.id,
            idGraph = t.idGraph,
            email = t.email,
            firstName = t.firstName,
            lastName = t.lastName,
            isBlockedUser = t.isBlockedUser,
            role = t.role
        )
    }
}