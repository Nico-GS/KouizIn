package com.app.quiz.controllers.user

import com.app.quiz.dto.PasswordResetRequest
import com.app.quiz.entities.user.User
import com.app.quiz.exceptions.user.UserNotFoundException
import com.app.quiz.services.storage.StorageService
import com.app.quiz.services.user.UsersService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user")
class UserController
{

    // region Autowired

    @Autowired
    private lateinit var storageService: StorageService

    @Autowired
    private lateinit var usersService: UsersService

    // endregion

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody email: String)
    {
        LOGGER.info("Mail envoyé à l'user $email")
        this.usersService.forgotPassword(email)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody request: PasswordResetRequest)
    {
        this.usersService.resetPassword(request.token, request.newPassword)
    }

    /**
     * Delete a [User]
     */
    @PutMapping
    fun updateUser(@RequestBody user: User): ResponseEntity<User>
    {
        val updatedUser = this.usersService.updateUser(user)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @PutMapping("/change-password/{userId}")
    fun updateUserPassword(
        @PathVariable userId: Long,
        @RequestBody newPassword: String
    )
    {
        this.usersService.updateUserPassword(userId, newPassword)
    }

    /**
     * Find [User] by his ID or null if not found
     */
    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<User>
    {
        val user = this.usersService.findUserById(id)
        return if (user != null)
        {
            ResponseEntity(user, HttpStatus.OK)
        } else
        {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Find all the [User]s
     */
    @GetMapping
    fun findAllUsers(): ResponseEntity<List<User>>
    {
        val users = this.usersService.findAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    /**
     * Upload an user profile picture
     */
    @PostMapping("/upload/{id}")
    fun uploadProfilePicture(@PathVariable id: Long, @RequestParam("file") file: MultipartFile): ResponseEntity<User>
    {
        val user = this.usersService.findUserById(id) ?: throw UserNotFoundException("User not found with id $id")
        val filePath = this.storageService.storeProfilePicture(file, user.id)
        user.pathProfilePicture = filePath

        this.usersService.updateUser(user)

        return ResponseEntity.ok(user)
    }

    /**
     * Get the path to the user profile picture
     *
     */
    @GetMapping("/path-pp/{userId}")
    fun getPathToProfilePictureByUserId(@PathVariable userId: Long): ResponseEntity<String>
    {
        val path = this.usersService.getPathToProfilePictureByUserId(userId)
        LOGGER.info(path)
        return ResponseEntity.ok(path)
    }

    companion object
    {

        val LOGGER = LoggerFactory.getLogger(UserController::class.java)
    }
}