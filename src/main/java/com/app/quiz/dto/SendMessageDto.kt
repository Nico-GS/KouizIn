package com.app.quiz.dto

data class SendMessageDto(
        val senderId: Long,
        val receiverId: Long,
        val content: String
)