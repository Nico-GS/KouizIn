package com.app.quiz.repositories.quiz

import com.app.quiz.entities.quiz.SessionQuiz
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionQuizRepository : JpaRepository<SessionQuiz, Long>
{

    fun findByQuizId(quizId: Long): SessionQuiz?

    fun findAllByQuizId(quizId: Long): List<SessionQuiz>?

    fun findAllByUserId(userId: Long): List<SessionQuiz>
}