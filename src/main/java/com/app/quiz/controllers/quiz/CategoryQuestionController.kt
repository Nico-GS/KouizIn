package com.app.quiz.controllers.quiz

import com.app.quiz.entities.quiz.CategoryQuestion
import com.app.quiz.services.quiz.CategoryQuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/question-category")
class CategoryQuestionController
{

    // region Autowired

    @Autowired
    private lateinit var categoryQuestionService: CategoryQuestionService

    // endregion

    @PostMapping("/create")
    fun createCategory(@RequestBody categoryQuestion: CategoryQuestion): ResponseEntity<CategoryQuestion>
    {
        val newCategory = categoryQuestionService.createCategory(categoryQuestion.name)
        return ResponseEntity.ok(newCategory)
    }

    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<List<CategoryQuestion>>
    {
        val categories = categoryQuestionService.getAllCategories()
        return ResponseEntity.status(HttpStatus.CREATED).body(categories)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestParam name: String): ResponseEntity<CategoryQuestion>
    {
        val updatedCategory = categoryQuestionService.updateCategory(id, name)
        return ResponseEntity.ok(updatedCategory)
    }


    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Unit>
    {
        categoryQuestionService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }


}