package com.app.quiz.dto

data class LoginDto(
        val email: String,
        val password: String,
)

data class RegisterDto(
        val email: String,
        val password: String,
        val pseudo: String
)

data class RegisterAdminDto(
        val email: String,
        val password: String
)

data class PasswordResetRequest(
        val token: String,
        val newPassword: String
)

data class FriendRequestDto(
        val userRequestId: Long,
        val friendId: Long
)

data class ConversationRequestDto(
        val senderId: Long,
        val receiverId: Long
)