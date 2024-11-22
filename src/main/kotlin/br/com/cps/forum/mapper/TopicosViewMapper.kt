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

        return TopicosView(
            id = t.id,
            title = t.title,
            slug = t.slug,
            privacy = t.privacy,
            section = t.section,
            body = t.body,
            tag = t.tag,
            answer = answersToList.toList(),
            user = t.user,
            votes = t.calculateQuantityVotes(),
            createdAt = t.createdAt,
            updatedAt = t.updatedAt
        )
    }

    fun mapToPage(t: Page<Topicos>): Page<TopicosView> {
        val topicosViewList = t.content.map { topico ->
            val answersToList = answer.listAnswers(topico.id)

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
                votes = topico.calculateQuantityVotes(),
                createdAt = topico.createdAt,
                updatedAt = topico.updatedAt
            )
        }

        return PageImpl(topicosViewList, t.pageable, t.totalElements)
    }
}