package com.app.quiz.dto

import com.app.quiz.entities.quiz.CategoryQuestion

/**
 * Request for create a quiz
 */
data class QuizCreateRequest(
    val description: String,
    val name: String,
    val isActive: Boolean,
    val questionIds: List<Long>
)

data class QuestionDto(
    val category: CategoryQuestion,
    val description: String,
    val answers: Set<AnswerDto>,
    var difficulty: Int,
    var points: Int
)

data class AnswerDto(
    val description: String,
    val isCorrect: Boolean
)

data class SessionQuizDto(
    val userId: Long,
    val quizId: Long,
    val duration: Int
)