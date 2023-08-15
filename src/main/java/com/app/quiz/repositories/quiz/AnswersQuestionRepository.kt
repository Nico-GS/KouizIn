package com.app.quiz.repositories.quiz

import com.app.quiz.entities.quiz.AnswersQuestion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnswersQuestionRepository : JpaRepository<AnswersQuestion, Long>
{

    fun findAllByQuestionId(questionId: Long): List<AnswersQuestion>

}