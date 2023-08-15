package com.app.quiz.controllers.social

import com.app.quiz.entities.social.Conversation
import com.app.quiz.services.social.ConversationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/social/conversations")
class ConversationController
{

    // region Autowired

    @Autowired
    private lateinit var conversationService: ConversationService

    // endregion

    /**
     * Create a new [Conversation] between two user
     */
    @PostMapping
    fun createConversation(@RequestBody conversation: Conversation): Conversation
    {
        return this.conversationService.createConversation(conversation)
    }

    /**
     * Delete a [Conversation] by ID
     */
    @DeleteMapping("/{id}")
    fun deleteConversation(@PathVariable id: String)
    {
        return this.conversationService.deleteConversation(id)
    }

    /**
     * Get a conversation between a [conversationRequestDto.senderId] and a [conversationRequestDto.receiverId]
     */
    @GetMapping("{senderId}/{receiverId}")
    fun getConversation(@PathVariable senderId: Long, @PathVariable receiverId: Long): ResponseEntity<Conversation?>
    {
        val conversation = this.conversationService.getConversation(senderId, receiverId)
        return ResponseEntity.ok(conversation)
    }
}