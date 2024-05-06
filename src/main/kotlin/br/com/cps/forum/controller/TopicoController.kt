package br.com.cps.forum.controller

import br.com.cps.forum.dto.TopicosForm
import br.com.cps.forum.dto.TopicosView
import br.com.cps.forum.dto.UpdateTopicosForm
import br.com.cps.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val service: TopicoService
) {
    @GetMapping
    fun getAllTopicos(
        @RequestParam(required = false) topicoName: String?,
        page: Pageable
    ): Page<TopicosView> {
        return service.listTopicos(topicoName, page)
    }

    @GetMapping("/{id}")
    fun getTopicosById(@PathVariable id: Long): TopicosView {
        return service.getById(id)
    }

    @PostMapping
    fun createTopicos(@RequestBody @Valid dtoTopico: TopicosForm): TopicosView {
        return service.createdTopicos(dtoTopico)
    }

    @PutMapping
    fun updateTopico(@RequestBody @Valid dtoTopico: UpdateTopicosForm): TopicosView {
        return service.updatedTopico(dtoTopico)
    }

    @DeleteMapping("/{id}")
    fun deleteTopico(@PathVariable id: Long) {
        return service.deletedTopico(id)
    }
}