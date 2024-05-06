package br.com.cps.forum.mapper

import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.model.Topicos
import org.springframework.stereotype.Component

@Component
class TopicosViewMapper() : Mapper<Topicos, TopicosView> {
    override fun map(t: Topicos): TopicosView {
        return TopicosView(
            id = t.id,
            title = t.title,
            privacy = t.privacy,
            section = t.section,
            body = t.body,
            tag = t.tag,
            answer = t.answer,
            user = t.user,
            created_at = t.created_at,
            updated_at = t.updated_at
        )
    }
}