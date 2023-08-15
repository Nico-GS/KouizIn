package com.app.quiz.services.mail

import com.sparkpost.Client
import com.sparkpost.model.responses.Response
import org.springframework.stereotype.Service

@Service
class PostmarkApiService
{

    fun validateAccount(): Response
    {
        val client = Client(API_KEY)

        client.authKey = API_KEY


        return client.sendMessage(
            "contact@kouiz.in",
            "contact@kouiz.in",
            "The subject of the message",
            "The text part of the email",
            "<b>The HTML part of the email</b>"
        )
    }

    companion object
    {
        private const val API_KEY = "e96ff262-3034-4216-86a9-3c7d814a730c"
    }
}