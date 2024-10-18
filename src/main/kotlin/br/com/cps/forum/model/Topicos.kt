package br.com.cps.forum.model

import br.com.cps.forum.model.enum.Privacy
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
data class Topicos(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String,
    var slug: String,
    var privacy: Privacy = Privacy.PUBLIC,
    var section: String,
    var body: String,
    var tag: String,
    @ManyToOne
    @JsonIgnore
    val user: User,
    @OneToMany(mappedBy = "topico", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val answer: List<Answers>? = null,
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")),
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")),
) : Serializable