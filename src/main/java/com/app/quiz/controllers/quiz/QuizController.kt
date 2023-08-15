package com.app.quiz.controllers.quiz

import com.app.quiz.dto.QuizCreateRequest
import com.app.quiz.entities.quiz.Quiz
import com.app.quiz.exceptions.quiz.QuizNotFoundException
import com.app.quiz.services.quiz.QuizService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz")
class QuizController
{

    // region Autowired

    @Autowired
    private lateinit var quizService: QuizService

    // endregion

    @PostMapping("/create")
    fun createQuizWithExistingQuestionsAndAnswers(@RequestBody quizCreateRequest: QuizCreateRequest): ResponseEntity<Quiz>
    {
        val quiz = quizService.createQuizWithExistingQuestionsAndAnswers(
            description = quizCreateRequest.description,
            name = quizCreateRequest.name,
            isActive = quizCreateRequest.isActive,
            questionIds = quizCreateRequest.questionIds
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz)
    }

    /**
     * Update a [Quiz]
     */
    @PutMapping("/update/{id}")
    fun updateQuiz(@PathVariable id: Long, @RequestBody quiz: Quiz): ResponseEntity<Quiz>
    {
        val quizToUpdate =
            this.quizService.findQuizById(id) ?: throw QuizNotFoundException("Quiz not found with ID : $id")
        return ResponseEntity.ok(this.quizService.updateQuiz(quizToUpdate))
    }

    @GetMapping("/reactivate/{quizId}")
    fun reactivateInactiveQuiz(@PathVariable quizId: Long)
    {
        return this.quizService.reactivateInactiveQuiz(quizId)
    }

    /**
     * Delete a [Quiz] and return it
     */
    @DeleteMapping("/{id}")
    fun deleteQuiz(@PathVariable id: Long): ResponseEntity<Unit>
    {
        this.quizService.deleteQuiz(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    /**
     * Find [Quiz] by his ID or null if not found
     */
    @GetMapping("/{id}")
    fun findQuizById(@PathVariable id: Long): ResponseEntity<Quiz>
    {
        val quiz = this.quizService.findQuizById(id)
        return if (quiz != null)
        {
            ResponseEntity(quiz, HttpStatus.OK)
        } else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Find all the [Quiz]es
     */
    @GetMapping
    fun findAllQuiz(): ResponseEntity<List<Quiz>>
    {
        val quizzes = this.quizService.findAllQuiz()
        return ResponseEntity(quizzes, HttpStatus.OK)
    }

    /**
     * Get a random Quiz ID
     */
    @GetMapping("/random-id")
    fun getRandomQuizId(): Long
    {
        return this.quizService.getRandomQuizId()
    }
}