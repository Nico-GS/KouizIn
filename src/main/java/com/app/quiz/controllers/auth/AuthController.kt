package com.app.quiz.controllers.auth

import com.app.quiz.dto.*
import com.app.quiz.entities.user.User
import com.app.quiz.enums.Role.*
import com.app.quiz.exceptions.user.UserNotFoundException
import com.app.quiz.services.auth.TokenService
import com.app.quiz.services.user.UsersService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.security.auth.login.LoginException

@RestController
@RequestMapping("/api")
class AuthController
{

    // region Autowired

    @Autowired
    private lateinit var userService: UsersService

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    // endregion

    /**
     * Login
     */
    @PostMapping("/login")
    @Throws(UserNotFoundException::class)
    fun login(@RequestBody payload: LoginDto): ResponseEntity<UserResponseDto>
    {
        val user = this.userService.findByEmail(payload.email)

        if (!passwordEncoder.matches(payload.password, user.password))
        {
            LOGGER.warn("Login failed for user ${user.id}")
            throw LoginException("Login failed")
        }

        // TODO path PP -> renvoyer string vide et pas  "https://backend.kouiz.in:8443/"
        val loginResponse = UserResponseDto(
            token = tokenService.createToken(user),
            role = user.role,
            userId = user.id,
            pathToProfilePicture = getPathToProfilePicture(user.id),
            pseudo = user.pseudo
        )

        return ResponseEntity.ok(loginResponse)
    }

    /**
     * Create a new [User]
     */
    @PostMapping("/register/new-user")
    fun registerUser(
        @RequestBody payload: RegisterDto,
        authentication: Authentication?
    ): ResponseEntity<UserResponseDto>
    {
        val user = User(
            email = payload.email,
            password = payload.password,
            pseudo = payload.pseudo,
            role = DEFAULT
        )

        val savedUser = this.userService.createUser(user)

        return ResponseEntity.ok(
            UserResponseDto(
                token = this.tokenService.createToken(savedUser),
                role = user.role,
                user.id,
                getPathToProfilePicture(user.id),
                pseudo = user.pseudo ?: ""
            )
        )
    }

    /**
     * Get the path to the profile picture
     */
    private fun getPathToProfilePicture(userId: Long): String
    {
        return this.userService.getPathToProfilePictureByUserId(userId)
    }

    companion object
    {

        val LOGGER = LoggerFactory.getLogger(AuthController::class.java)
    }
}


