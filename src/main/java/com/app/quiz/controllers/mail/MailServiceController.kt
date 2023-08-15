package com.app.quiz.controllers.mail

import com.app.quiz.services.mail.PostmarkApiService
import com.sparkpost.model.responses.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mail")
class MailServiceController
{

    // region Autowired

    @Autowired(required = true)
    private lateinit var postmarkApiService: PostmarkApiService

    // endregion


    @GetMapping("/send")
    fun getAllQuestions(): Response
    {
        val questions = this.postmarkApiService.validateAccount()
        return questions
    }
}