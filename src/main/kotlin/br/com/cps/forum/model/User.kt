package br.com.cps.forum.model

import br.com.cps.forum.model.enum.Reason
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val idGraph: Long? = null,
    val email: String,
    val password: String,
    var firstName: String,
    var lastName: String,
    var isBlockedUser: Boolean = false,
    var blockByReason: Reason = Reason.CLEAN,
    var blockedBy: String? = null,
    var unblockedBy: String? = null,

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val role: List<Role> = mutableListOf()
) : Serializable {
    fun messageUserIsBlocked(blocked: Boolean, email: String): String =
        if (blocked) "User '$email' is blocked!" else "User '$email' is unblocked!"
}