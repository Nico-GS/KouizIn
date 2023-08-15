package com.app.quiz.controllers.quiz

import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.services.quiz.AnswersQuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/answers-question")
class AnswersQuestionController
{

    // region Autowired

    @Autowired
    private lateinit var answersQuestionService: AnswersQuestionService

    // endregion

    /**
     * Create a new [AnswersQuestion] and return it
     */
    @PostMapping("/create")
    fun createAnswer(@RequestBody answer: AnswersQuestion): ResponseEntity<AnswersQuestion>
    {
        val newAnswer = this.answersQuestionService.createAnswer(answer)
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnswer)
    }

    @PutMapping("/{id}")
    fun updateAnswer(@PathVariable id: Long, @RequestBody answer: AnswersQuestion): ResponseEntity<AnswersQuestion>
    {
        val updatedAnswer = this.answersQuestionService.updateAnswer(id, answer)
        return ResponseEntity.ok(updatedAnswer)
    }

    @DeleteMapping("/{id}")
    fun deleteAnswer(@PathVariable id: Long): ResponseEntity<Unit>
    {
        this.answersQuestionService.deleteAnswer(id)
        return ResponseEntity.noContent().build()
    }

    /**
     * Find [AnswersQuestion] by his ID or null if not found
     */
    @GetMapping("/{id}")
    fun findAnswersQuestionById(@PathVariable id: Long): ResponseEntity<AnswersQuestion>
    {
        val answersQuestion = this.answersQuestionService.findAnswersQuestionById(id)
        return if (answersQuestion != null)
        {
            ResponseEntity(answersQuestion, HttpStatus.OK)
        } else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Find all the [AnswersQuestion]s
     */
    @GetMapping
    fun findAllAnswersQuestions(): ResponseEntity<List<AnswersQuestion>>
    {
        val answersQuestions = this.answersQuestionService.findAllAnswersQuestion()
        return ResponseEntity(answersQuestions, HttpStatus.OK)
    }
}