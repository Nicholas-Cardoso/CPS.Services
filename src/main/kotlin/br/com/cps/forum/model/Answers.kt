package br.com.cps.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
data class Answers(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var answerBody: String,
    @ManyToOne
    @JsonIgnore
    val topico: Topicos,
    @ManyToOne
    @JsonIgnore
    val user: User,
    var positiveVotes: Int = 0,
    var negativeVotes: Int = 0,
    @ManyToOne
    @JsonIgnore
    var answerFather: Answers? = null,
    @OneToMany(mappedBy = "answerFather", cascade = [CascadeType.ALL])
    val answerChild: MutableList<Answers> = mutableListOf(),
    val createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")),
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")),
) : Serializable {

    fun calculateQuantityVotes(): Int {
        return positiveVotes - negativeVotes
    }
}
