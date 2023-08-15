package com.app.quiz.services.social

import com.app.quiz.entities.social.Conversation
import com.app.quiz.entities.social.Message
import com.app.quiz.event.NewMessageEvent
import com.app.quiz.event.config.KouizEventPublisher
import com.app.quiz.exceptions.user.UserNotFoundException
import com.app.quiz.repositories.social.ConversationRepository
import com.app.quiz.repositories.social.MessageRepository
import com.app.quiz.repositories.user.UserRepository
import com.app.quiz.services.social.secu.EncryptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService
{

    // region Autowired

    @Autowired
    private lateinit var conversationRepository: ConversationRepository

    @Autowired
    private lateinit var encryptionService: EncryptionService

    @Autowired
    private lateinit var publisher: KouizEventPublisher

    @Autowired
    private lateinit var messageRepository: MessageRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    // endregion

    fun deleteMessage(id: String)
    {
        this.messageRepository.deleteById(id)
    }

    /**
     * Send a [Message] to a user
     *
     * The message is encrypted in database
     */
    fun sendMessage(senderId: Long, receiverId: Long, content: String)
    {

        val userSender = this.userRepository.findByIdOrNull(senderId)
        val userReceiver = this.userRepository.findByIdOrNull(receiverId)

        if (userSender == null)
        {
            throw UserNotFoundException("User not found with ID $senderId - Can't send the message")
        }
        if (userReceiver == null)
        {
            throw UserNotFoundException("User not found with ID $receiverId - Can't send the message")
        }

        /**
         * ID of the conversation : the [senderId] and the [receiverId]
         */
        val conversationId = "$senderId-$receiverId"

        /**
         * Encrypt the message
         */
        val encryptedMessage = this.encryptionService.encrypt(content)

        /**
         * Create the message and save it
         */
        val message = Message(
            id = UUID.randomUUID().toString(),
            senderId = senderId,
            receiverId = receiverId,
            conversationId = conversationId,
            content = encryptedMessage
        )
        this.messageRepository.save(message)

        /**
         * Check if already exist existingConversations between the two user
         */
        val existingConversations =
            this.conversationRepository.findBySenderIdInAndReceiverIdIn(
                listOf(senderId, receiverId),
                listOf(senderId, receiverId)
            )

        /**
         * If [senderId] 1 send a [Message] to [receiverId] 2, create a new conversation if don't exist, or add the message
         * If [senderId] 2 send a [Message] to [receiverId] 1, use the same conversation,
         */
        val existingConversation = existingConversations.firstOrNull {
            (it.senderId == senderId && it.receiverId == receiverId) || (it.senderId == receiverId && it.receiverId == senderId)
        }

        /**
         * Add messages to the [Conversation]
         */
        if (existingConversation != null)
        {
            existingConversation.messages += message
            this.conversationRepository.save(existingConversation)

            /**
             * Create a [NewMessageEvent]
             */
            this.publisher.publish(NewMessageEvent(message))
        } else
        {
            /**
             * Or create a new [Conversation]
             */
            val conversation = Conversation(senderId = senderId, receiverId = receiverId, messages = listOf(message))
            this.conversationRepository.save(conversation)

            /**
             * Create a [NewMessageEvent]
             */
            this.publisher.publish(NewMessageEvent(message))
        }
    }
}