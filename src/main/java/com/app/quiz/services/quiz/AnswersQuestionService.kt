package com.app.quiz.services.quiz

import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.exceptions.answers.AnswersQuestionNotFoundException
import com.app.quiz.repositories.quiz.AnswersQuestionRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class AnswersQuestionService
{

    // region Autowired

    @Autowired
    private lateinit var answersQuestionRepository: AnswersQuestionRepository

    // endregion

    /**
     * Create a new [AnswersQuestion] and return it
     */
    @Transactional
    open fun createAnswer(answer: AnswersQuestion): AnswersQuestion
    {
        return this.answersQuestionRepository.save(answer)
    }

    /**
     * Update an [AnswersQuestion]
     */
    @Transactional
    open fun updateAnswer(id: Long, answer: AnswersQuestion): AnswersQuestion
    {
        val existingAnswer =
            this.answersQuestionRepository.findById(id)
                .orElseThrow { AnswersQuestionNotFoundException("AnswersQuestion not found with ID $id") }
        existingAnswer.description = answer.description
        existingAnswer.isCorrect = answer.isCorrect
        return this.answersQuestionRepository.save(existingAnswer)
    }

    /**
     * Delete an [AnswersQuestion]
     */
    @Transactional
    open fun deleteAnswer(id: Long)
    {
        return this.answersQuestionRepository.deleteById(id)
    }

    /**
     * Find all the [AnswersQuestion]s by ID
     */
    @Transactional
    open fun findAnswersQuestionById(answersQuestionId: Long): AnswersQuestion?
    {
        return this.answersQuestionRepository.findById(answersQuestionId).orElse(null)
    }

    /**
     * Find all the [AnswersQuestion]s
     */
    @Transactional
    open fun findAllAnswersQuestion(): List<AnswersQuestion>
    {
        return this.answersQuestionRepository.findAll()
    }
}