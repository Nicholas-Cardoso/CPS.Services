package br.com.cps.forum.model

import br.com.cps.forum.enum.Privacy
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
data class Topicos(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var privacy: Privacy = Privacy.PUBLIC,
    var section: String,
    var body: String,
    var tag: String,
    @ManyToOne
    @JsonIgnore
    val user: Usuario,
    @OneToMany(mappedBy = "topico", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val answer: List<Answers>? = null,
    val created_at: LocalDateTime = LocalDateTime.now(),
    var updated_at: LocalDateTime = LocalDateTime.now(),
) : Serializable