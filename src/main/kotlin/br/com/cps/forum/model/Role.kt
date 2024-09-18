package br.com.cps.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
data class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,
    private val name: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "role")
    val user: List<User> = mutableListOf()
) : GrantedAuthority {
    override fun getAuthority() = name

    fun getName() = name
}