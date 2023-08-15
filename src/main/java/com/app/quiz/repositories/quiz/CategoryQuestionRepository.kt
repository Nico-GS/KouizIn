package com.app.quiz.repositories.quiz

import com.app.quiz.entities.quiz.CategoryQuestion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryQuestionRepository : JpaRepository<CategoryQuestion, Long>
{
    fun findByName(name: String): CategoryQuestion?
}
