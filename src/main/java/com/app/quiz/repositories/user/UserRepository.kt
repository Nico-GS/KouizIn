package com.app.quiz.repositories.user

import com.app.quiz.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>
{
    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User

    fun findByResetToken(token: String): User?

}