package com.app.quiz.services.utils

import com.app.quiz.entities.quiz.AnswersQuestion
import com.app.quiz.entities.quiz.CategoryQuestion
import com.app.quiz.entities.quiz.Question
import com.app.quiz.repositories.questions.QuestionRepository
import com.app.quiz.repositories.quiz.CategoryQuestionRepository
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class QuizProcessingService
{

    // region Autowired

    @Autowired
    private lateinit var questionRepository: QuestionRepository

    @Autowired
    private lateinit var categoryQuestionRepository: CategoryQuestionRepository

    // endregion



//    fun processAndSaveQuestionsFromJson(file: MultipartFile)
//    {
//
//        require(!file.isEmpty) { "Empty file" }
//
//        val jsonString = String(file.bytes, Charsets.UTF_8)
//
//        val json = Json {
//            prettyPrint = true
//            ignoreUnknownKeys = true
//        }
//        val questionnaire = json.decodeFromString(Questionnaire.serializer(), jsonString)
//
//        questionnaire.quizz["fr"]?.let { quizzContent ->
//            // Convertir et enregistrer les questions
//            val allQuestions =
//                quizzContent.débutant + (quizzContent.confirmé) + (quizzContent.expert)
//
//            allQuestions.forEach { quizEntry ->
//                val question = convertToQuestionEntity(quizEntry)
//                questionRepository.save(question)
//            }
//        }
//    }

    // region Private methods

//    private fun convertToQuestionEntity(quizEntry: QuizEntry): Question
//    {
//        val question = Question()
//
//        // Fixer la catégorie
//        val category =
//            this.categoryQuestionRepository.findByName("Culture générale")
//                ?: CategoryQuestion(name = "Culture générale").also {
//                    this.categoryQuestionRepository.save(it)
//                }
//        question.category = category
//
//        question.description = quizEntry.question
//        question.difficulty = when (quizEntry.id)
//        {  // Remplacez ceci en fonction de votre logique d'attribution de la difficulté
//            in 1..10 -> 1
//            in 11..20 -> 2
//            else -> 3
//        }
//        question.points = 10  // Attribuez des points si nécessaire
//        question.anecdote = quizEntry.anecdote
//
//        quizEntry.propositions.forEachIndexed { _, proposition ->
//            val isCorrect = proposition == quizEntry.réponse
//            val answer = AnswersQuestion(question = question, description = proposition, isCorrect = isCorrect)
//            question.answers.add(answer)
//        }
//
//        return question
//    }

    // endregion

}