package com.app.quiz.entities.quiz

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "question")
class Question(

    @ManyToOne(fetch = LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "category_id", nullable = false)
    var category: CategoryQuestion? = null,

    @Column(nullable = false)
    var description: String? = null,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = EAGER)
    // Handle bidirectional relationship
    @JsonManagedReference
    var answers: MutableSet<AnswersQuestion> = mutableSetOf(),

    @ManyToMany(mappedBy = "questions", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JsonIgnore
    var quizzes: MutableSet<Quiz> = HashSet(),

    @Column(name = "difficulty", nullable = false)
    var difficulty: Int = 0,

    @Column(name = "points", nullable = false)
    var points: Int = 0,

    @Column(name = "anecdote", nullable = true)
    var anecdote: String? = null

)
{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0
}
