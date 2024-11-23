package br.com.cps.forum.controller

import br.com.cps.forum.dto.VotesForm
import br.com.cps.forum.service.VotesService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/votes")
class VotesController(
    private val votesService: VotesService
) {
    @PostMapping("/topico/{topicoId}")
    fun voteOnTopico(
        @PathVariable topicoId: Long,
        @RequestBody @Valid voteForm: VotesForm
    ) {
        return votesService.voteOnTopico(topicoId, voteForm)
    }

    @PostMapping("/answer/{answerId}")
    fun voteOnAnswer(
        @PathVariable answerId: Long,
        @RequestBody @Valid voteForm: VotesForm
    ) {
        return votesService.voteOnAnswer(answerId, voteForm)
    }
}