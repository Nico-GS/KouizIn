package com.app.quiz.entities.quiz

import com.app.quiz.entities.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.Duration
import java.time.LocalDateTime

@Entity
@Table(name = "session_quiz", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "start_time"])])

class SessionQuiz(

        @ManyToOne
    @JoinColumn(name = "quiz_id")
    var quiz: Quiz = Quiz(),

        @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = User(),

        @Column(name = "start_time")
    var startTime: LocalDateTime = LocalDateTime.now(),

        @Column(name = "timer")
    var timer: Duration = Duration.ZERO,

        @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

        @Column(name = "score", nullable = false)
    var score: Int = 0
)
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_quiz_id_seq")
    @SequenceGenerator(name = "session_quiz_id_seq", allocationSize = 1)
    var id: Long = 0
}