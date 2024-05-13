package br.com.cps.forum.mapper

import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.service.AnswerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

private val page: Pageable = PageRequest.of(0, 10)

@Component
class TopicosViewMapper(
    private val answer: AnswerService,
    private val mapperToView: AnswerViewMapper
) : Mapper<Topicos, TopicosView> {
    override fun map(t: Topicos): TopicosView {
        return TopicosView(
            id = t.id,
            title = t.title,
            privacy = t.privacy,
            section = t.section,
            body = t.body,
            tag = t.tag,
            answer = mapperToView.mapToAnswersList(t.answer),
            user = t.user,
            created_at = t.created_at,
            updated_at = t.updated_at
        )
    }

    fun mapToPage(t: Page<Topicos>): Page<TopicosView> {
        val topicosViewList = t.content.map { topico ->
            val answersToList = answer.listAnswers(topico.id, page)
            TopicosView(
                id = topico.id,
                title = topico.title,
                privacy = topico.privacy,
                section = topico.section,
                body = topico.body,
                tag = topico.tag,
                answer = answersToList.toList(),
                user = topico.user,
                created_at = topico.created_at,
                updated_at = topico.updated_at
            )
        }

        return PageImpl(topicosViewList, t.pageable, t.totalElements)
    }
}