package com.app.quiz.services.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class QuizProcessingController
{

    // region Autowired

    @Autowired
    private lateinit var quizProcessingService: QuizProcessingService

    // endregion

//    @PostMapping("/json")
//    fun convertJsonToQuestions(@RequestParam("file") file: MultipartFile)
//    {
//        return this.quizProcessingService.processAndSaveQuestionsFromJson(file)
//    }

}