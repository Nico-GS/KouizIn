package com.app.quiz.services.quiz

import com.app.quiz.entities.quiz.Quiz
import com.app.quiz.exceptions.quiz.QuizInactiveException
import com.app.quiz.exceptions.quiz.QuizNotFoundException
import com.app.quiz.repositories.quiz.QuizRepository
import com.app.quiz.repositories.questions.QuestionRepository
import com.app.quiz.repositories.quiz.SessionQuizRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
open class QuizService
{
    // region Autowired

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var sessionQuizRepository: SessionQuizRepository

    // endregion

    /**
     * Create a new [Quiz] and return it
     */
    @Transactional
    open fun createQuizWithExistingQuestionsAndAnswers(
        description: String,
        name: String,
        isActive: Boolean,
        questionIds: List<Long>
    ): Quiz
    {
        val questions = this.questionRepository.findAllById(questionIds)
        val quiz = Quiz(description = description, name = name, isActive = isActive, questions = questions)
        return this.quizRepository.save(quiz)
    }

    /**
     * Reactivate a [Quiz]
     */
    @Transactional
    open fun reactivateInactiveQuiz(quizId: Long)
    {
        val quiz = this.quizRepository.findByIdOrNull(quizId)
            ?: throw QuizNotFoundException("Quiz not found with id $quizId")

        if (quiz.isActive)
        {
            throw QuizInactiveException("Quiz $quizId already active")
        }

        quiz.also {
            it.isActive = true
            quizRepository.saveAndFlush(it)
        }
    }

    /**
     * Delete a [Quiz] and dissociate questions
     */
    @Transactional
    open fun deleteQuiz(quizId: Long)
    {
        val quiz: Quiz =
            quizRepository.findById(quizId).orElseThrow { QuizNotFoundException("Quiz not found with id $quizId") }

        val sessionQuizzes = this.sessionQuizRepository.findAllByQuizId(quizId)

        sessionQuizzes?.forEach { sessionQuiz ->
            sessionQuizRepository.delete(sessionQuiz)
        }

        quiz.questions?.clear()

        this.quizRepository.save(quiz)

        this.quizRepository.delete(quiz)
    }

    /**
     * Update a [Quiz]
     */
    @Transactional
    open fun updateQuiz(quiz: Quiz): Quiz
    {
        val currentQuiz = this.quizRepository.findByIdOrNull(quiz.id)
            ?: throw QuizNotFoundException("Quiz not found with ID : ${quiz.id}")

        currentQuiz.also {
            it.description = quiz.description
            it.name = quiz.name
            it.questions = quiz.questions
        }

        return this.quizRepository.saveAndFlush(currentQuiz)
    }

    /**
     * Find [Quiz] by his ID or null if not found
     */
    @Transactional
    open fun findQuizById(quizId: Long): Quiz?
    {
        return this.quizRepository.findById(quizId).orElse(null)
    }

    /**
     * Find all the [Quiz]s
     */
    @Transactional
    open fun findAllQuiz(): List<Quiz>
    {
        return this.quizRepository.findAll()
    }

    /**
     * Get a random Quiz ID
     */
    @Transactional
    open fun getRandomQuizId(): Long
    {
        return this.quizRepository.getRandomActiveQuizId().findFirst().orElseThrow {
            QuizNotFoundException("Quiz not found")
        }

    }
}