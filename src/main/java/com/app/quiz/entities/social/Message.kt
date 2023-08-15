package com.app.quiz.entities.social

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "messages")
data class Message(

        // TODO UUID + no constructor
        @Id
        val id: String? = null,

        val senderId: Long,

        val receiverId: Long,

        val conversationId: String,

        val content: String,

        val timestamp: Instant = Instant.now()
)