package com.app.quiz.entities.quiz

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "answers_question")
class AnswersQuestion(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = true)
//    @JsonIgnore
    @JsonBackReference
    var question: Question = Question(),

    var description: String = "",

    var isCorrect: Boolean = true
)
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ans_question_id_seq")
    @SequenceGenerator(name = "ans_question_id_seq", allocationSize = 1)
    var id: Long = 0
}