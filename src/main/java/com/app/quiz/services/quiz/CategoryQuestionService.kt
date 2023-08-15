package com.app.quiz.services.quiz

import com.app.quiz.entities.quiz.CategoryQuestion
import com.app.quiz.exceptions.category_question.CategoryQuestionAlreadyExistException
import com.app.quiz.repositories.quiz.CategoryQuestionRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CategoryQuestionService
{

    // region Autowired

    @Autowired
    private lateinit var categoryQuestionRepository: CategoryQuestionRepository

    // endregion

    /**
     * Get all [CategoryQuestion]s
     */
    @Transactional
    open fun getAllCategories(): List<CategoryQuestion>
    {
        return categoryQuestionRepository.findAll()
    }

    /**
     * Create a new [CategoryQuestion]
     */
    @Transactional
    open fun createCategory(name: String): CategoryQuestion
    {
        val existingCategory = categoryQuestionRepository.findByName(name)
        if (existingCategory != null)
        {
            throw CategoryQuestionAlreadyExistException("Category with name $name already exists")
        }
        val newCategory = CategoryQuestion(name = name)
        return categoryQuestionRepository.save(newCategory)
    }

    /**
     * Update a [CategoryQuestion] by ID
     */
    @Transactional
    open fun updateCategory(id: Long, name: String): CategoryQuestion
    {
        val existingCategory = categoryQuestionRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Category with id $id not found") }
        existingCategory.name = name
        return categoryQuestionRepository.save(existingCategory)
    }

    /**
     * Delete a [CategoryQuestion] by ID
     */
    @Transactional
    open fun deleteCategory(id: Long)
    {
        require(this.categoryQuestionRepository.existsById(id)) { "Category with id $id not found" }
        this.categoryQuestionRepository.deleteById(id)
    }

}