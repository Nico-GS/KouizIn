package com.app.quiz.controllers.advice

import com.app.quiz.exceptions.answers.AnswersQuestionNotFoundException
import com.app.quiz.exceptions.category_question.CategoryNotFoundException
import com.app.quiz.exceptions.category_question.CategoryQuestionAlreadyExistException
import com.app.quiz.exceptions.questions.QuestionNotFoundException
import com.app.quiz.exceptions.quiz.*
import com.app.quiz.exceptions.score_user.ScoreUserNotFoundException
import com.app.quiz.exceptions.session_quiz.SessionQuizNotFoundException
import com.app.quiz.exceptions.social.FriendshipAlreadyExistsException
import com.app.quiz.exceptions.storage.UploadProfilePictureException
import com.app.quiz.exceptions.user.RegisterUserException
import com.app.quiz.exceptions.user.UserAlreadyRegisteredException
import com.app.quiz.exceptions.user.UserLoginFailedException
import com.app.quiz.exceptions.user.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * This class is used for the response status when an [RuntimeException] is thrown
 */
@ControllerAdvice
class ExceptionHandlerController
{

    // region Quiz & Questions

    @ExceptionHandler(QuizInactiveException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleQuizInactiveException(ex: QuizInactiveException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(QuizNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleQuizNotFoundException(ex: QuizNotFoundException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(SessionQuizNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleSessionQuizNotFoundException(ex: SessionQuizNotFoundException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(AnswersQuestionNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleAnswersQuestionNotFoundException(ex: AnswersQuestionNotFoundException): String
    {
        return ex.message ?: "Answers question not found"
    }

    @ExceptionHandler(QuestionNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleQuestionNotFoundException(ex: QuestionNotFoundException): String
    {
        return ex.message ?: "Question not found"
    }

    @ExceptionHandler(CategoryQuestionAlreadyExistException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleCategoryQuestionAlreadyExistException(ex: CategoryQuestionAlreadyExistException): String
    {
        return ex.message ?: "Category question already exists"
    }

    @ExceptionHandler(CategoryNotFoundException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleCategoryQuestionAlreadyExistException(ex: CategoryNotFoundException): String
    {
        return ex.message ?: "Category question not found"
    }

    // endregion

    // region User

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleUserNotFoundException(ex: UserNotFoundException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(ScoreUserNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    fun handleScoreUserNotFoundException(ex: ScoreUserNotFoundException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(UserAlreadyRegisteredException::class)
    @ResponseStatus(I_AM_A_TEAPOT)
    @ResponseBody
    fun handleUserAlreadyRegisteredException(ex: UserAlreadyRegisteredException): String
    {
        return "${ex.message}"
    }

    @ExceptionHandler(RegisterUserException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleRegisterUserException(ex: RegisterUserException): String
    {
        return ex.message ?: "Error registering user"
    }

    @ExceptionHandler(UserLoginFailedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleUserLoginFailedException(ex: UserLoginFailedException): String
    {
        return ex.message ?: "User login failed"
    }

    // endregion

    // region Upload

    @ExceptionHandler(UploadProfilePictureException::class)
    @ResponseStatus(I_AM_A_TEAPOT)
    @ResponseBody
    fun handleUploadProfilePictureException(ex: UploadProfilePictureException): String
    {
        return ex.message ?: "Error uploading profile picture"
    }

    // endregion

    // region Friendship

    @ExceptionHandler(FriendshipAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleFriendshipAlreadyExistException(ex: FriendshipAlreadyExistsException): String
    {
        return ex.message ?: "Category question already exists"
    }

    // endregion


}