package br.com.cps.forum.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val idGraph: Long? = null,
    val email: String,
    var firstName: String,
    var lastName: String
) : Serializable