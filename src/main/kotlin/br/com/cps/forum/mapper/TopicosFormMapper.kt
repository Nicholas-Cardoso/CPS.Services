package br.com.cps.forum.mapper

import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.extension.getUserById
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class TopicosFormMapper(
    private val userRepository: UserRepository
) : Mapper<TopicosForm, Topicos> {
    override fun map(t: TopicosForm): Topicos {
        return Topicos(
            title = t.title,
            privacy = t.privacy,
            section = t.section,
            body = t.body,
            tag = t.tag,
            user = t.getUserById(userRepository, t.userId),
        )
    }
}