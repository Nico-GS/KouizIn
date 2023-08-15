package com.app.quiz.repositories.social

import com.app.quiz.entities.social.Message
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<Message, String>
{

}