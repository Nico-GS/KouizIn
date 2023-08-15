package com.app.quiz.services.utils

import kotlinx.serialization.Serializable

/**
 * This data class represent the openquizzdb format
 */
@Serializable
data class Questionnaire(
    val fournisseur: String,
    val rédacteur: String,
    val difficulté: String,
    val version: Int,
    val mise_à_jour: String? = null,
    val catégorie_nom_slogan: Map<String, Map<String, String>>? = null,
    val quizz: Map<String, QuizzContent>
)