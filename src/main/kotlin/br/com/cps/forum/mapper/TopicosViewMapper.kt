package br.com.cps.forum.mapper

import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.extension.transformNameToSlug
import br.com.cps.forum.extension.transformTitleToSlug
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.service.AnswerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@Component
class TopicosViewMapper(
    private val answer: AnswerService
) : Mapper<Topicos, TopicosView> {
    override fun map(t: Topicos): TopicosView {
        val answersToList = answer.listAnswers(t.id)

        if (t.user.slug.isNullOrBlank()) {
            t.user.slug = transformNameToSlug(t.user.firstName, t.user.lastName)
        }

        return TopicosView(
            id = t.id,
            title = t.title,
            slug = transformTitleToSlug(t.title),
            privacy = t.privacy,
            section = t.section,
            body = t.body,
            tag = t.tag,
            answer = answersToList.toList(),
            user = t.user,
            createdAt = t.createdAt,
            updatedAt = t.updatedAt
        )
    }

    fun mapToPage(t: Page<Topicos>): Page<TopicosView> {
        val topicosViewList = t.content.map { topico ->
            val answersToList = answer.listAnswers(topico.id)
            if (topico.user.slug.isNullOrBlank()) {
                topico.user.slug = transformNameToSlug(topico.user.firstName, topico.user.lastName)
            }

            TopicosView(
                id = topico.id,
                title = topico.title,
                slug = transformTitleToSlug(topico.title),
                privacy = topico.privacy,
                section = topico.section,
                body = topico.body,
                tag = topico.tag,
                answer = answersToList.toList(),
                user = topico.user,
                createdAt = topico.createdAt,
                updatedAt = topico.updatedAt
            )
        }

        return PageImpl(topicosViewList, t.pageable, t.totalElements)
    }
}