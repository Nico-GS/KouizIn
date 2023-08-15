package com.app.quiz.repositories.quiz

import com.app.quiz.entities.quiz.Quiz
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

@Repository
interface QuizRepository : JpaRepository<Quiz, Long>
{

        @Query("SELECT q.id FROM Quiz q WHERE q.isActive = true ORDER BY RANDOM()")
        @Transactional(readOnly = true)
        fun getRandomActiveQuizId(): Stream<Long>
}