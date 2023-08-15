package com.app.quiz.services.utils

import kotlinx.serialization.Serializable

/**
 * This data class represent the openquizzdb format
 */
@Serializable
data class QuizEntry(
    val id: Int,
    val question: String,
    val propositions: List<String>,
    val réponse: String,
    val anecdote: String
)