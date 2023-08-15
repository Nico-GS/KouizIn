package com.app.quiz.repositories.questions

import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.entities.quiz.Question
import com.app.quiz.entities.quiz.Quiz

interface QuestionCustomRepository
{

    fun getQuestionByQuizzes(quiz: Quiz): List<Question>
    fun findAllWithAnswersByQuestionId(questionId: Long): List<Pair<Question, AnswersQuestion>>
}
