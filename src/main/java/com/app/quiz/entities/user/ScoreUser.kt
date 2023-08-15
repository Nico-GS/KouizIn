package com.app.quiz.entities.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "score_user")
class ScoreUser(

        @OneToOne
    @JoinColumn(name = "user_id")
    var user: User = User(),

        @Column(name = "score", nullable = false)
    var score: Int = 0
)
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_user_id_seq")
    @SequenceGenerator(name = "score_user_id_seq", allocationSize = 1)
    var id: Long = 0
}