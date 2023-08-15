package com.app.quiz.controllers.quiz

import com.app.quiz.dto.QuestionDto
import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.entities.quiz.Question
import com.app.quiz.repositories.quiz.QuizRepository
import com.app.quiz.services.question.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/question")
class QuestionController
{

    // region Autowired

    @Autowired
    private lateinit var questionService: QuestionService

    @Autowired
    private lateinit var quizRepository: QuizRepository

    // endregion

    /**
     * Get all the [Question]s
     */
    @GetMapping("/all")
    fun getAllQuestions(): ResponseEntity<Pair<MutableList<Question>, MutableList<AnswersQuestion>>>
    {
        val questions = this.questionService.getAllQuestions()
        return ResponseEntity.ok(questions)
    }

    /**
     * Get a [Question] by ID
     */
    @GetMapping("/{id}")
    fun getQuestionById(@PathVariable id: Long): List<Pair<Question, AnswersQuestion>>
    {
        val question = this.questionService.getQuestionsAndAnswersByQuestionId(id)
        return question
    }

    @GetMapping("/list/{quizId}")
    fun getQuestionByQuizId(@PathVariable quizId: Long): ResponseEntity<List<Question>>
    {

        val quiz = this.quizRepository.findById(quizId)
        return if (quiz.isPresent)
        {
            ResponseEntity.ok(this.questionService.getQuestionsByQuizId(quizId))
        } else
        {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Add a new [Question]
     *
     * @param questionDto :
     *
     * - [QuestionDto.description]
     * - [QuestionDto.category]
     * - [QuestionDto.difficulty]
     * - [QuestionDto.points]
     * - [QuestionDto.answers]
     */
    @PostMapping("/create")
    fun createQuestion(@RequestBody questionDto: QuestionDto): ResponseEntity<Question>
    {
        val newQuestion = this.questionService.createQuestion(questionDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion)
    }

    /**
     * Update a [Question]
     *
     * @param id - The [Question] id
     */
    @PutMapping("/{id}")
    fun updateQuestion(@PathVariable id: Long, @RequestBody question: Question): ResponseEntity<Question>
    {
        val updatedQuestion = this.questionService.updateQuestion(id, question)
        return ResponseEntity.ok(updatedQuestion)
    }

    /**
     * Delete a [Question]
     *
     * @param id - The [Question] id
     */
    @DeleteMapping("/{id}")
    fun deleteQuestion(@PathVariable id: Long): ResponseEntity<Unit>
    {
        this.questionService.deleteQuestion(id)
        return ResponseEntity.noContent().build()
    }
}