package br.com.cps.forum.model

import jakarta.persistence.*

@Entity
@Table(
    name = "votes",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "topico_id"]),
        UniqueConstraint(columnNames = ["user_id", "answer_id"])
    ]
)
data class Votes(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var positiveVotes: Int = 0,
    var negativeVotes: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "topico_id")
    val topicos: Topicos? = null,

    @ManyToOne
    @JoinColumn(name = "answer_id")
    val answers: Answers? = null,

    @Column(nullable = false)
    var hasVoted: Boolean = true
)