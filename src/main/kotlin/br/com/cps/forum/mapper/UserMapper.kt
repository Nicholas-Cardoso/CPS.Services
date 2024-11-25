package br.com.cps.forum.mapper

import br.com.cps.forum.dto.UserView
import br.com.cps.forum.model.User
import br.com.cps.forum.repository.VotesRepository
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val votesRepository: VotesRepository
) : Mapper<User, UserView> {

    override fun map(t: User): UserView {
        return UserView(
            id = t.id!!,
            oId = t.oId,
            email = t.email,
            name = t.name,
            slug = t.slug,
            isBlockedUser = t.isBlockedUser,
            blockByReason = t.blockByReason,
            blockedBy = t.blockedBy,
            unblockedBy = t.unblockedBy,
            role = t.role.map { it.getName() },
            totalVotes = votesRepository.countTotalVotesForUser(t.id)
        )
    }

    fun mapperUserAllToView(t: List<User>): List<UserView> {
        return t.map { user ->
            UserView(
                id = user.id!!,
                oId = user.oId,
                email = user.email,
                name = user.name,
                slug = user.slug,
                isBlockedUser = user.isBlockedUser,
                blockByReason = user.blockByReason,
                blockedBy = user.blockedBy,
                unblockedBy = user.unblockedBy,
                role = user.role.map { it.getName() },
                totalVotes = votesRepository.countTotalVotesForUser(user.id)
            )
        }
    }
}