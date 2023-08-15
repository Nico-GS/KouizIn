package com.app.quiz.services.quiz

import com.app.quiz.entities.quiz.SessionQuiz
import com.app.quiz.exceptions.quiz.QuizInactiveException
import com.app.quiz.repositories.quiz.QuizRepository
import com.app.quiz.repositories.quiz.SessionQuizRepository
import com.app.quiz.repositories.user.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

@Service
open class SessionQuizService
{

    // region Autowired

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var sessionQuizRepository: SessionQuizRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    // endregion

    /**
     * Start a [Quiz]
     *
     * @param userId : The [User.id]
     * @param quizId : The [Quiz.id]
     * @param duration : The time duration of the quiz in seconds
     */
    @Async
    @Cacheable("quiz")
    @Transactional
    @Throws(QuizInactiveException::class)
    open fun startQuiz(userId: Long, quizId: Long, duration: Int): CompletableFuture<SessionQuiz>?
    {

        /**
         * Get the current user, the quiz to start and the session quiz if exist
         */
        val user = this.userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val quiz = this.quizRepository.findById(quizId).orElseThrow { Exception("Quiz not found") }

        /**
         * And create a new [SessionQuiz]
         */
        val sessionQuiz = SessionQuiz().also {
            it.quiz = quiz
            it.user = user
            it.startTime = LocalDateTime.now()
            it.timer = Duration.ofSeconds(duration.toLong())
            it.isActive = true
            sessionQuizRepository.saveAndFlush(it)
        }

        return CompletableFuture.completedFuture(sessionQuiz)
    }

    /**
     * Get the remaining time of a Quiz
     */
    @Transactional
    open fun getRemainingTime(sessionQuizId: Long): Duration
    {
        val sessionQuiz =
                sessionQuizRepository.findById(sessionQuizId).orElseThrow { Exception("SessionQuiz not found") }

        if (!sessionQuiz.isActive)
        {
            LOGGER.warn("No remaning time for quiz ${sessionQuiz.quiz.id}")
            throw QuizInactiveException("No remaning time for quiz ${sessionQuiz.quiz.id}")
        }

        val elapsedTime = Duration.between(sessionQuiz.startTime, LocalDateTime.now())
        val remainingTime = sessionQuiz.timer.minus(elapsedTime)

        if (remainingTime.isNegative || remainingTime.isZero)
        {
            this.endQuiz(sessionQuizId)
        }

        return remainingTime
    }

    // TODO refaire ça plus tard
    /**
     * End all Quiz started when the duration is expired
     */
    //    @Scheduled(fixedRate = 2000) // 1s
    //    @Transactional
    //    open fun checkQuizTimes()
    //    {
    //        val ongoingQuizzes = sessionQuizRepository.findAllByQuizIsActiveTrue()
    //
    //        for (sessionQuiz in ongoingQuizzes)
    //        {
    //
    //            if (!sessionQuiz.isActive)
    //            {
    //                continue
    //            }
    //
    //            val remainingTime = this.getRemainingTime(sessionQuiz.id)
    //
    //            if (remainingTime.isZero || remainingTime.isNegative)
    //            {
    //                LOGGER.info("Quiz ${sessionQuiz.id}: is finished")
    //                this.endQuiz(sessionQuiz.id)
    //            } else
    //            {
    //                LOGGER.info("Remaining time for quiz ${sessionQuiz.id}: $remainingTime")
    //            }
    //        }
    //    }

    /**
     * End a Quiz - isActive -> true for now
     */
    @Transactional
    open fun endQuiz(quizId: Long): List<SessionQuiz>
    {

        /**
         * Get all the [SessionQuiz] by quiz ID
         */
        val sessionQuizzes = this.sessionQuizRepository.findAllByQuizId(quizId)

        if (!sessionQuizzes.isNullOrEmpty())
        {
            val endedSessionQuizzes = mutableListOf<SessionQuiz>()

            for (sessionQuiz in sessionQuizzes)
            {
                val quiz = sessionQuiz.quiz
                sessionQuiz.isActive = false

                quiz.also {
                    LOGGER.info("Quiz $quizId terminé")
                    this.quizRepository.saveAndFlush(it)
                }

                sessionQuiz.also {
                    it.isActive = false
                    LOGGER.info("SessionQuiz ${sessionQuiz.id} terminé")
                    this.sessionQuizRepository.saveAndFlush(it)
                }

                endedSessionQuizzes.add(sessionQuiz)
            }
            return endedSessionQuizzes

        }
        throw QuizInactiveException("Quiz pas found")
    }

    /**
     * Get [SessionQuiz] by quiz ID
     */
    @Transactional
    open fun getByQuizId(quizId: Long): SessionQuiz?
    {
        return this.sessionQuizRepository.findByQuizId(quizId)
    }

    /**
     * Get all [SessionQuiz] by user ID
     */
    @Transactional
    open fun findAllByUserId(userId: Long): List<SessionQuiz>
    {
        return this.sessionQuizRepository.findAllByUserId(userId)
    }

    companion object
    {

        val LOGGER = LoggerFactory.getLogger(SessionQuizService::class.java)
    }
}