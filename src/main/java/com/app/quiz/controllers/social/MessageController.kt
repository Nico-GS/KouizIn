package com.app.quiz.controllers.social

import com.app.quiz.dto.SendMessageDto
import com.app.quiz.entities.social.Message
import com.app.quiz.services.social.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/social/messages")
class MessageController
{

    // region Autowired

    @Autowired
    private lateinit var messageService: MessageService

    // endregion

    /**
     * Delete a [Message]
     */
    @DeleteMapping("/{id}")
    fun deleteMessage(@PathVariable id: String)
    {
        return this.messageService.deleteMessage(id)
    }

    /**
     * Send a new [Message] to another user
     */
    @PostMapping("/send")
    fun sendMessage(@RequestBody message: SendMessageDto)
    {
        this.messageService.sendMessage(message.senderId, message.receiverId, message.content)
    }

}