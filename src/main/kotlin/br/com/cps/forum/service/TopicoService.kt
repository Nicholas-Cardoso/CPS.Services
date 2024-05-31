package br.com.cps.forum.service

import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.dto.UpdateTopicosForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.mapper.TopicosFormMapper
import br.com.cps.forum.mapper.TopicosViewMapper
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.network.api.MailService
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.until.builderMailTopico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.net.SocketTimeoutException
import java.time.LocalDateTime

private const val notFoundMessage: String = "Tópico não encontrado."

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val mapperToForm: TopicosFormMapper,
    private val mapperToView: TopicosViewMapper,
    private val mailService: MailService
) {
    //    @Cacheable(cacheNames = ["Topicos"])
    fun listTopicos(
        topicoName: String?,
        page: Pageable
    ): Page<TopicosView> {
        val topicos = topicoName?.let {
            repository.findByTitle(topicoName, page)
        } ?: repository.findAll(page)

        return mapperToView.mapToPage(topicos)
    }

    //    @Cacheable(cacheNames = ["Topicos"], key = "#id")
    fun getById(id: Long): TopicosView {
        val topicoByid = findByTopico(id)
        return mapperToView.map(topicoByid)
    }

    //    @CacheEvict(cacheNames = ["Topicos", "Answer"], allEntries = true)
    fun createdTopicos(dtoTopico: TopicosForm): TopicosView {
        val toMapper = mapperToForm.map(dtoTopico)
        val created = repository.save(toMapper)
        val topicView = mapperToView.map(created)

        try {
            val builder = builderMailTopico(created.user)
            mailService.sendMails(builder).execute()
        } catch (e: SocketTimeoutException) {
            println(e.message)
        }

        return topicView
    }

    //    @CachePut(cacheNames = ["Topicos", "Answer"], key = "#dtoTopico.id")
    fun updatedTopico(dtoTopico: UpdateTopicosForm): TopicosView {
        val existingTopico = findByTopico(dtoTopico.id)

        dtoTopico.let {
            existingTopico.title = it.title
            existingTopico.privacy = it.privacy
            existingTopico.section = it.section
            existingTopico.body = it.body
            existingTopico.tag = it.tag
            existingTopico.updated_at = LocalDateTime.now()
        }
        repository.save(existingTopico)
        return mapperToView.map(existingTopico)
    }

    //    @CacheEvict(cacheNames = ["Topicos", "Answer"], allEntries = true)
    fun deletedTopico(id: Long) {
        val findTopico = findByTopico(id)
        return repository.deleteById(findTopico.id!!)
    }

    private fun findByTopico(id: Long): Topicos {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException(notFoundMessage)
            }
    }
}
