package br.com.cps.forum.service

import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.dto.UpdateTopicosForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.extension.checkAndSaveBrainAI
import br.com.cps.forum.mapper.TopicosFormMapper
import br.com.cps.forum.mapper.TopicosViewMapper
import br.com.cps.forum.model.Role
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.network.api.HashBrainService
import br.com.cps.forum.network.api.MailService
import br.com.cps.forum.repository.TopicoRepository
import br.com.cps.forum.until.builderMailTopico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.net.SocketTimeoutException
import java.time.LocalDateTime

private const val notFoundMessage: String = "Tópico não encontrado."

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val mapperToForm: TopicosFormMapper,
    private val mapperToView: TopicosViewMapper,
    private val mailService: MailService,
    private val brainService: HashBrainService
) {
    fun listTopicos(
        topicoName: String?,
        page: Pageable
    ): Page<TopicosView> {
        val topicos = topicoName?.let {
            repository.findByTitle(topicoName, page)
        } ?: repository.findAll(page)

        return mapperToView.mapToPage(topicos)
    }

    fun getById(id: Long): TopicosView {
        val topicoByid = findByTopico(id)
        return mapperToView.map(topicoByid)
    }

    fun createdTopicos(dtoTopico: TopicosForm): TopicosView {
        val authentication = SecurityContextHolder.getContext().authentication.authorities.first()
        val userId = (authentication as Role).id

        val toMapper = mapperToForm.map(dtoTopico, userId)

        val created = checkAndSaveBrainAI(
            brainService,
            toMapper,
            repository,
            bodyExtractor = { appendStrings(it.title, it.body) })

        val topicView = mapperToView.map(created)

        try {
            val builder = builderMailTopico(created.user)
            mailService.sendMails(builder).execute()
        } catch (e: SocketTimeoutException) {
            println("Timeout: ${e.message}")
            throw e
        } catch (e: Exception) {
            println(e.message)
            throw e
        }

        return topicView
    }

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

        checkAndSaveBrainAI(
            brainService,
            existingTopico,
            repository,
            bodyExtractor = { appendStrings(it.title, it.body) })

        return mapperToView.map(existingTopico)
    }

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

    private fun appendStrings(title: String, body: String): String {
        return StringBuilder()
            .append(title)
            .append(" ")
            .append(body)
            .toString()

    }
}
