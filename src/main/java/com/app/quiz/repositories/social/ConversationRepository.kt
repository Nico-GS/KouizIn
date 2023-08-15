package com.app.quiz.repositories.social

import com.app.quiz.entities.social.Conversation
import org.springframework.data.mongodb.repository.MongoRepository

interface ConversationRepository : MongoRepository<Conversation, String>
{
    fun findBySenderIdInAndReceiverIdIn(senderId: List<Long>, receiverId: List<Long>): List<Conversation>

    fun findAllBySenderId(senderId: Long): List<Conversation>
}