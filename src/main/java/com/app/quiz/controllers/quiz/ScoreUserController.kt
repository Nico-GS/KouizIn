package com.app.quiz.controllers.quiz

import com.app.quiz.entities.user.ScoreUser
import com.app.quiz.services.user.ScoreUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scores")
class ScoreUserController
{

    // region Autowired

    @Autowired
    private lateinit var scoreUserService: ScoreUserService

    // endregion

    @GetMapping("/{userId}")
    fun getUserScore(@PathVariable userId: Long): ResponseEntity<Int>
    {
        val score = this.scoreUserService.getUserScoreByUserId(userId)
        return ResponseEntity.ok(score)
    }

    @GetMapping("/all")
    fun getAllScoreUser(): List<ScoreUser>?
    {
        return this.scoreUserService.getAllScoreUser()
    }
}