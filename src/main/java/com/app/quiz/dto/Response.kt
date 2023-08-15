package com.app.quiz.dto;

import com.app.quiz.enums.Role

/**
 * This file contains all outgoing DTOs.
 */
data class UserResponseDto(
    val token: String,
    val role: Role,
    val userId: Long,
    val pathToProfilePicture: String?,
    val pseudo: String?,
)