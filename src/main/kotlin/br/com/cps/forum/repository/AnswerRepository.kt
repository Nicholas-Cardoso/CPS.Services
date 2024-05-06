package br.com.cps.forum.repository

import br.com.cps.forum.model.Answers
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository: JpaRepository<Answers, Long> {
    fun findByTopicoId(topicoId: Long, page: Pageable): Page<Answers>

    fun findByAnswerFatherId(parentId: Long?): List<Answers>
}
