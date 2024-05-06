package br.com.cps.forum.service

import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.dto.UpdateTopicosForm
import br.com.cps.forum.exception.NotFoundException
import br.com.cps.forum.mapper.TopicosFormMapper
import br.com.cps.forum.mapper.TopicosViewMapper
import br.com.cps.forum.model.Topicos
import br.com.cps.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private const val notFoundMessage: String = "Tópico não encontrado."

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val mapperToForm: TopicosFormMapper,
    private val mapperToView: TopicosViewMapper
) {
    //    @Cacheable(value = ["Topicos"], key = "#root.method.name")
    fun listTopicos(
        topicoName: String?,
        page: Pageable
    ): Page<TopicosView> {
        val topicos = if (topicoName != null) {
            repository.findByTitle(topicoName, page)
        } else {
            repository.findAll(page)
        }

        return topicos.map {
            mapperToView.map(it)
        }
    }

    fun getById(id: Long): TopicosView {
        val topicoByid = findByTopico(id)
        return mapperToView.map(topicoByid)
    }

    fun createdTopicos(dtoTopico: TopicosForm): TopicosView {
        val toMapper = mapperToForm.map(dtoTopico)
        val created = repository.save(toMapper)
        return mapperToView.map(created)
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
        repository.save(existingTopico)
        return mapperToView.map(existingTopico)
    }

    fun deletedTopico(id: Long) {
        return repository.deleteById(id)
    }

    private fun findByTopico(id: Long): Topicos {
        return repository.findById(id)
            .orElseThrow {
                NotFoundException(notFoundMessage)
            }
    }
}
