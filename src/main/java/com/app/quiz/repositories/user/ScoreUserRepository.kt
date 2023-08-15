package com.app.quiz.repositories.user

import com.app.quiz.entities.user.ScoreUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScoreUserRepository : JpaRepository<ScoreUser, Long>
{
    fun findByUserId(userId: Long): ScoreUser?
}