package com.app.quiz.entities.quiz

import jakarta.persistence.*

@Entity
@Table(name = "quiz")
class Quiz(

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var name: String = "",

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "quiz_question",
        joinColumns = [JoinColumn(name = "quiz_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id", nullable = true)]
    )
    var questions: MutableList<Question>? = null,

    @Column(nullable = false)
    var isActive: Boolean = true

)
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_id_seq")
    @SequenceGenerator(name = "quiz_id_seq", allocationSize = 1)
    var id: Long = 0

}
