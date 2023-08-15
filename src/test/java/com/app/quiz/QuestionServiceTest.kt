package com.app.quiz

import com.app.quiz.repositories.questions.QuestionRepository
import com.app.quiz.repositories.quiz.QuizRepository
import com.app.quiz.services.quiz.QuizService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest
class QuestionServiceTest
{

    // region Autowired

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var quizService: QuizService

    // endregion

    @Test
    fun testAnswers()
    {

        val filePath = "/resources/uploads/profile_pictures/1_nest_ce_pas.jpg"
        val file = Paths.get(filePath)
        val exists = Files.exists(file)

        println("File : $exists")
    }

    @Test
    fun testCreateQuizWithExistingQuestionsAndAnswers()
    {
        val description = "Test 1"
        val name = "Math"
        val isActive = true
        val questionIds = listOf(1L, 2L)

        val quiz = this.quizService.createQuizWithExistingQuestionsAndAnswers(description, name, isActive, questionIds)

        assertNotNull(quiz)
        assertEquals(description, quiz.description)
        assertEquals(name, quiz.name)
        assertEquals(isActive, quiz.isActive)
    }
}