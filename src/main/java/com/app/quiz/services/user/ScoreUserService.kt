package com.app.quiz.services.user

import com.app.quiz.entities.user.ScoreUser
import com.app.quiz.exceptions.score_user.ScoreUserNotFoundException
import com.app.quiz.exceptions.session_quiz.SessionQuizNotFoundException
import com.app.quiz.repositories.user.ScoreUserRepository
import com.app.quiz.repositories.quiz.SessionQuizRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ScoreUserService
{

    // region Autowired

    @Autowired
    private lateinit var scoreUserRepository: ScoreUserRepository

    @Autowired
    private lateinit var sessionQuizRepository: SessionQuizRepository

    // endregion

    @Transactional
    open fun getUserScoreByUserId(userId: Long): Int
    {
        val scoreUser = this.scoreUserRepository.findByUserId(userId)
            ?: throw ScoreUserNotFoundException("Score not found for user with id $userId")

        return scoreUser.score
    }

    @Transactional
    open fun getAllScoreUser(): List<ScoreUser>?
    {
        return this.scoreUserRepository.findAll()
    }

    /**
     * Update the score [User]
     */
    @Transactional
    open fun updateScore(sessionQuizId: Long, score: Int)
    {
        val sessionQuizOpt = this.sessionQuizRepository.findById(sessionQuizId)
        if (sessionQuizOpt.isPresent)
        {
            val sessionQuiz = sessionQuizOpt.get()
            var scoreUser = this.scoreUserRepository.findByUserId(sessionQuiz.user.id)
            if (scoreUser == null)
            {
                scoreUser = ScoreUser(user = sessionQuiz.user, score = 0)
            }
            scoreUser.score += score
            sessionQuiz.score += score
            sessionQuiz.isActive = false
            this.scoreUserRepository.saveAndFlush(scoreUser)
        } else
        {
            throw SessionQuizNotFoundException("No SessionQuiz found with id $sessionQuizId")
        }
    }
}