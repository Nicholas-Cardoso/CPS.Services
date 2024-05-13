package br.com.cps.forum.controller

import br.com.cps.forum.dto.AnswerForm
import br.com.cps.forum.dto.AnswerUpdateForm
import br.com.cps.forum.dto.AnswerView
import br.com.cps.forum.dto.AnswersView
import br.com.cps.forum.service.AnswerService
import jakarta.validation.Valid
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/answer")
class AnswerController(
    private val service: AnswerService
) {
    @GetMapping
    fun getAllAnswers(
        @RequestParam(required = false) topicoId: Long?,
        page: Pageable
    ): Page<AnswersView> {
        return service.listAnswers(topicoId, page)
    }

    @GetMapping("/{id}")
    fun getAnswerById(@PathVariable id: Long): AnswerView {
        return service.getById(id)
    }

    @PostMapping
    fun createAnswer(
        @RequestBody @Valid answerForm: AnswerForm,
        @RequestParam(required = false) parentId: Long?
    ): AnswerView {
        return service.createAnswer(answerForm, parentId)
    }

    @PutMapping
    fun updateAnswer(@RequestBody @Valid answerUpdate: AnswerUpdateForm): AnswerView {
        return service.updateAnswer(answerUpdate)
    }

    @DeleteMapping("/{id}")
    fun deleteAnswer(@PathVariable id: Long) {
        return service.deleteAnswer(id)
    }
}