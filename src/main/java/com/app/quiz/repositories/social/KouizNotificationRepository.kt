package com.app.quiz.repositories.social

import com.app.quiz.entities.social.KouizNotification
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface KouizNotificationRepository: MongoRepository<KouizNotification, UUID>