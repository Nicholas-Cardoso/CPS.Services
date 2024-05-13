package br.com.cps.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable

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
    val user: Usuario,
    @ManyToOne
    @JsonIgnore
    var answerFather: Answers? = null,
    @OneToMany(mappedBy = "answerFather")
    val answerChild: MutableList<Answers> = mutableListOf()
) : Serializable
