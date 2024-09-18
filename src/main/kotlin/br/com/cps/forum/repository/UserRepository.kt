package br.com.cps.forum.repository

import br.com.cps.forum.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User?

    fun findUserById(id: Long): User?
}
