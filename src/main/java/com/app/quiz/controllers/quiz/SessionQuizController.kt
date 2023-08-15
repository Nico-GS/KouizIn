package com.app.quiz.controllers.quiz

import com.app.quiz.dto.SessionQuizDto
import com.app.quiz.entities.quiz.SessionQuiz
import com.app.quiz.services.user.ScoreUserService
import com.app.quiz.services.quiz.SessionQuizService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/session")
class SessionQuizController {

    // region Autowired

    @Autowired
    private lateinit var sessionQuizService: SessionQuizService

    @Autowired
    private lateinit var scoreUserService: ScoreUserService

    // endregion

    @PostMapping("/start")
    fun startQuiz(@RequestBody sessionQuizDto: SessionQuizDto): ResponseEntity<SessionQuiz> {
        val futureSessionQuiz =
            this.sessionQuizService.startQuiz(sessionQuizDto.userId, sessionQuizDto.quizId, sessionQuizDto.duration)
        val sessionQuiz = futureSessionQuiz?.get() ?: throw Exception("Failed to start quiz")
        return ResponseEntity.ok(sessionQuiz)
    }

    @GetMapping("/end/{quizId}")
    fun endQuiz(@PathVariable("quizId") quizId: Long): ResponseEntity<SessionQuiz> {
        this.sessionQuizService.endQuiz(quizId)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/update-score")
    fun updateScoreSessionQuiz(@RequestBody sessionQuiz: SessionQuiz) {
        this.scoreUserService.updateScore(sessionQuiz.id, sessionQuiz.score)
    }

    @GetMapping("/remaining-time")
    fun getRemainingTime(@RequestParam sessionQuizId: Long): ResponseEntity<Duration> {
        val remainingTime = this.sessionQuizService.getRemainingTime(sessionQuizId)
        return ResponseEntity.ok(remainingTime)
    }

    @GetMapping("/{quizId}")
    fun getSessionQuizByQuizId(@PathVariable quizId: Long): ResponseEntity<SessionQuiz> {
        return ResponseEntity.ok(this.sessionQuizService.getByQuizId(quizId))
    }

    @GetMapping("/all/{userId}")
    fun getAllByUserId(@PathVariable userId: Long): ResponseEntity<List<SessionQuiz>> {
        return ResponseEntity.ok(this.sessionQuizService.findAllByUserId(userId))
    }

    //    @GetMapping("/active/{userId}")
    //    fun getByUserIdAndActiveTrue(@PathVariable userId: Long): ResponseEntity<Boolean?>
    //    {
    //        return ResponseEntity.ok(this.sessionQuizService.getByUserIdAndActiveTrue(userId))
    //    }
}