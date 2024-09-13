package br.com.cps.forum.repository

import br.com.cps.forum.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(username: String?): User?
}
