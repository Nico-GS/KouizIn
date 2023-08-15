package com.app.quiz.services.question

import com.app.quiz.dto.ExtractionQuestionDto
import com.app.quiz.dto.QuestionDto
import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.entities.quiz.CategoryQuestion
import com.app.quiz.entities.quiz.Question
import com.app.quiz.exceptions.category_question.CategoryNotFoundException
import com.app.quiz.exceptions.questions.QuestionNotFoundException
import com.app.quiz.exceptions.quiz.QuizInactiveException
import com.app.quiz.repositories.questions.QuestionRepository
import com.app.quiz.repositories.quiz.AnswersQuestionRepository
import com.app.quiz.repositories.quiz.CategoryQuestionRepository
import com.app.quiz.repositories.quiz.QuizRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
open class QuestionService
{

    // region Autowired

    @Autowired
    private lateinit var answersQuestionRepository: AnswersQuestionRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var categoryQuestionRepository: CategoryQuestionRepository

    @Autowired
    private lateinit var quizRepository: QuizRepository

    // endregion

    /**
     * Get all the [Question]s
     */
    @Transactional
    open fun getAllQuestions(): Pair<MutableList<Question>, MutableList<AnswersQuestion>>
    {
        val questions = this.questionRepository.findAll()
        val answers = this.answersQuestionRepository.findAll()

        return Pair(questions, answers)
    }

    /**
     * Get a [Question] by ID
     */
    @Transactional
    open fun getQuestionsAndAnswersByQuestionId(id: Long):
            List<Pair<Question, AnswersQuestion>>
    {
        return this.questionRepository.findAllWithAnswersByQuestionId(id)
    }

    @Transactional
    open fun getQuestionsByQuizId(quizId: Long): List<Question>
    {
        val quiz = this.quizRepository.findByIdOrNull(quizId) ?: throw QuizInactiveException("Quiz $quizId not found")
        return this.questionRepository.getQuestionByQuizzes(quiz)
    }

    /**
     * Add a new [Question]
     */
    @Transactional
    open fun createQuestion(questionDto: QuestionDto): Question
    {

        val category = this.categoryQuestionRepository.findById(questionDto.category.id).orElseThrow {
            CategoryNotFoundException("Category not found with id ${questionDto.category.id}")
        }

        val question = Question(
            category = category,
            description = questionDto.description,
            difficulty = questionDto.difficulty,
            points = questionDto.points
        )

        // Save it before or transactional error
        val savedQuestion = this.questionRepository.save(question)

        val savedAnswers = questionDto.answers.map { answerDto ->
            val answer = AnswersQuestion(
                question = savedQuestion,
                description = answerDto.description,
                isCorrect = answerDto.isCorrect
            )
            this.answersQuestionRepository.save(answer)
        }.toMutableSet()

        savedQuestion.answers = savedAnswers

        return savedQuestion
    }

    /**
     * Submit a [Question] to the community
     */
    @Transactional
    open fun submitQuestion(questionDto: QuestionDto): Question
    {
       return TODO()
    }

    /**
     * Update a [Question]
     */
    @Transactional
    open fun updateQuestion(id: Long, question: Question): Question
    {
        val existingQuestion =
            this.questionRepository.findById(id)
                .orElseThrow { QuestionNotFoundException("Question not found with ID $id") }

        existingQuestion.also {
            it.description = question.description
            if (question.category?.id != null)
            {
                it.category = categoryQuestionRepository.findById(question.category!!.id).orElse(question.category)
            } else
            {
                it.category = question.category
            }
            it.difficulty = question.difficulty
            it.points = question.points
        }

        return this.questionRepository.saveAndFlush(existingQuestion)
    }


    /**
     * Delete a [Question] by ID
     */
    @Transactional
    open fun deleteQuestion(id: Long)
    {
        val question = this.questionRepository.findById(id)

        // Delete all associations in "quiz_question"
        question.ifPresent { q ->
            q.quizzes.forEach { quiz ->
                quiz.questions?.remove(q)
            }
            val answers = this.answersQuestionRepository.findAllByQuestionId(id)
            this.answersQuestionRepository.deleteAll(answers)
            this.questionRepository.delete(q)
        }
    }

}