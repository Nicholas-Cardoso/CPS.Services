package br.com.cps.forum.model

import br.com.cps.forum.model.enum.Reason
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

//@Entity
//data class UsersBlock(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long? = null,
//    val reason: Reason = Reason.INAPPROPRIATE_CONTENT,
//    val messageReason: String,
//    val adminEmail: String,
//
//    @ManyToOne
//    val userId: User
//)
