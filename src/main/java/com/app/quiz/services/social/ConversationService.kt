package com.app.quiz.services.social

import com.app.quiz.entities.social.Conversation
import com.app.quiz.repositories.social.ConversationRepository
import com.app.quiz.services.social.secu.EncryptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConversationService
{

    // region Autowired

    @Autowired
    private lateinit var conversationRepository: ConversationRepository

    @Autowired
    private lateinit var encryptionService: EncryptionService

    // endregion

    /**
     * Create a new [Conversation] between two user
     */
    fun createConversation(conversation: Conversation): Conversation
    {
        return this.conversationRepository.save(conversation)
    }

    /**
     * Delete a [Conversation] by ID
     */
    fun deleteConversation(id: String)
    {
        return this.conversationRepository.deleteById(id)
    }

    /**
     * Get a conversation between a [senderId] and a [receiverId]
     */
    fun getConversation(senderId: Long, receiverId: Long): Conversation?
    {

        val conversations = this.conversationRepository.findBySenderIdInAndReceiverIdIn(
                listOf(senderId, receiverId),
                listOf(senderId, receiverId)
        )

        /**
         * Double sens
         * Sender peut être receiver et inversement
         *
         */
        val conversation = conversations.firstOrNull {
            (it.senderId == senderId && it.receiverId == receiverId) || (it.senderId == receiverId && it.receiverId == senderId)
        }

        return conversation?.apply {
            messages = messages.map { message ->
                val decryptedContent = encryptionService.decrypt(message.content)
                message.copy(id = message.id, content = decryptedContent)
            }
        }
    }

}