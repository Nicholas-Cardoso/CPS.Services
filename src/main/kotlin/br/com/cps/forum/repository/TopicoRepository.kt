package br.com.cps.forum.repository

import br.com.cps.forum.model.Topicos
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository : JpaRepository<Topicos, Long> {
    fun findByTitle(
        topicoName: String?,
        page: Pageable
    ): Page<Topicos>
}
