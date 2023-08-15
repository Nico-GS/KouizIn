package com.app.quiz.entities.social

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "conversations")
data class Conversation(

        @Id
        val id: String? = null,

        val senderId: Long,

        val receiverId: Long,

        var messages: List<Message> = mutableListOf()
)