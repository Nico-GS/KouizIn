package com.app.quiz.services.utils

import com.app.quiz.exceptions.quiz.QuizNotFoundException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class JsonUtilsService
{


    /**
     * Send a JSON with questions in multiples languages and keep only french
     * Return a String -> the JSON
     */
    fun filterFrench(file: MultipartFile): String {

        require(!file.isEmpty) { "Empty file" }

        val jsonString = String(file.bytes, Charsets.UTF_8)

        /**
         * JSON configuration :
         * ignoreUnknownKeys: Allows the Json to ignore properties present in the JSON
         */
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        val questionnaire = json.decodeFromString<Questionnaire>(jsonString)

        val frenchQuestions = questionnaire.quizz["fr"]?.let { fr ->
            QuizzContent(
                débutant = fr.débutant.orEmpty(),
                confirmé = fr.confirmé.orEmpty(),
                expert = fr.expert.orEmpty()
            )
        } ?: QuizzContent(emptyList(), emptyList(), emptyList())

        val frenchCategory = questionnaire.catégorie_nom_slogan?.get("fr")?.let { mapOf("fr" to it) } ?: emptyMap()

        return json.encodeToString(
            questionnaire.copy(
                catégorie_nom_slogan = frenchCategory,
                quizz = mapOf("fr" to frenchQuestions)
            )
        )
    }
}

