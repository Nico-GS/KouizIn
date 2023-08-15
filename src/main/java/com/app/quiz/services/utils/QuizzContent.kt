package com.app.quiz.services.utils

import kotlinx.serialization.Serializable

/**
 * This data class represent the openquizzdb format
 */
@Serializable
data class QuizzContent(
    val débutant: List<QuizEntry>,
    val confirmé: List<QuizEntry>,
    val expert: List<QuizEntry>
)