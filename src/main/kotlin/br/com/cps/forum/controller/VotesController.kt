package br.com.cps.forum.controller

import br.com.cps.forum.dto.VotesForm
import br.com.cps.forum.service.VotesService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/votes")
class VotesController(
    private val votesService: VotesService
) {

    @PostMapping
    fun registerVotes(@RequestBody votesForm: VotesForm) = votesService.countingVotesToTopicsOrAnswers(votesForm)
}