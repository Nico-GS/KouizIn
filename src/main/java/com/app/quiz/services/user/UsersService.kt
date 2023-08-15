package com.app.quiz.services.user

import com.app.quiz.entities.user.User
import com.app.quiz.exceptions.user.UserAlreadyRegisteredException
import com.app.quiz.exceptions.user.UserNotFoundException
import com.app.quiz.repositories.user.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
open class UsersService
{
    // region Autowired

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    private lateinit var mailSender: JavaMailSender

    // endregion

    // region Values

    @Value("\${app.server-url}")
    private lateinit var serverURL: String

    // endregion

    // region CRUD User

    /**
     * Create a new [User] and return it
     */
    @Transactional
    open fun createUser(user: User): User
    {
        if (this.userRepository.existsByEmail(user.email)) throw UserAlreadyRegisteredException("User already registered")

        user.password = this.passwordEncoder.encode(user.password)
        this.userRepository.saveAndFlush(user)
        return user
    }

    /**
     * Update user password
     */
    @Transactional
    open fun updateUserPassword(userId: Long, newPassword: String)
    {
        val user = this.userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException("User not found with ID $userId")

        if (this.passwordEncoder.matches(newPassword, user.password))
        {
            LOGGER.warn("PASSWORD MATCH ENCULER : newPassword : $newPassword")
            throw UserNotFoundException("PASSWORD MATCH ENCULER")
        } else
        {

            user.password = this.passwordEncoder.encode(newPassword)

            this.userRepository.saveAndFlush(user)
        }
    }

    /**
     * Update an [User] and return it (without password)
     */
    @Transactional
    open fun updateUser(user: User): User
    {

        val existingUser = this.userRepository.findByIdOrNull(user.id)

        existingUser?.also {
            it.firstName = user.firstName
            it.lastName = user.lastName
            it.email = user.email
            it.phoneNumber = user.phoneNumber
            it.pathProfilePicture = user.pathProfilePicture
            it.pseudo = user.pseudo
        } ?: throw UserNotFoundException("User not found with ID : ${user.id}")

        return this.userRepository.saveAndFlush(existingUser)
    }

    /**
     * Find [User] by his ID or null if not found
     */
    @Transactional
    open fun findUserById(userId: Long): User?
    {
        return this.userRepository.findById(userId).orElse(null)
    }

    @Transactional
    open fun findByEmail(email: String): User
    {
        return this.userRepository.findByEmail(email)
    }

    /**
     * Find all the [User]s
     */
    @Transactional
    open fun findAllUsers(): List<User>
    {
        return this.userRepository.findAll()
    }

    // endregion

    // region Profile picture

    /**
     * Get the path to the [User] profile picture by his ID
     * Example : http://kouiz.in:8080/uploads/pp/1_jdg.jpg
     */
    @Transactional
    open fun getPathToProfilePictureByUserId(userId: Long): String
    {
        val user = this.userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException("User not found with ID $userId")
        var userPath = user.pathProfilePicture ?: ""

        // Remove the 'static/' in the user path to profile picture
        val staticPrefix = "static/"
        if (userPath.startsWith(staticPrefix))
        {
            userPath = userPath.substring(staticPrefix.length)
        }
        userPath = userPath.replace("\\", "/")

        return serverURL + userPath
    }

    // endregion

    // region Forgot / Reset password

    // TODO faire un html/css pour le mail
    @Transactional
    open fun forgotPassword(email: String)
    {

        val user = this.userRepository.findByEmail(email)

        val resetToken = UUID.randomUUID().toString()
        user.resetToken = resetToken
        this.userRepository.saveAndFlush(user)

        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true)
        helper.setTo(user.email)
        helper.setSubject("Reset your password")
        helper.setText(
            "<h1>Reset Your Password</h1>" +
                    "<p>To reset your password, click the link below:</p>" +
                    "<a href=\"https://kouiz.in/resetPassword?token=$resetToken\">" +
                    "Reset Password</a>", true
        )

        mailSender.send(mimeMessage)
    }

    open fun resetPassword(token: String, newPassword: String)
    {
        val user = this.userRepository.findByResetToken(token)
            ?: throw IllegalArgumentException("Invalid reset token")

        user.password = passwordEncoder.encode(newPassword)
        user.resetToken = null
        userRepository.save(user)
    }

    // endregion

    companion object
    {

        val LOGGER = LoggerFactory.getLogger(UsersService::class.java)
    }
}