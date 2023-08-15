package com.app.quiz.entities.quiz

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "question_category")
data class CategoryQuestion(

        @Column(nullable = false)
        @JsonProperty("name")
        var name: String = "",
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}