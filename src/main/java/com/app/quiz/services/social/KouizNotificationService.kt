package com.app.quiz.services.social


import com.app.quiz.entities.social.Friendship
import com.app.quiz.entities.social.KouizNotification
import com.app.quiz.entities.social.Message
import com.app.quiz.event.KouizNotificationTypeSealedWrapper
import com.app.quiz.repositories.social.KouizNotificationRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class KouizNotificationService
{

    // region Autowired

    @Autowired
    private lateinit var kouizNotificationRepository: KouizNotificationRepository

    // endregion

    /**
     * Create a new [KouizNotification]
     */
    fun create(type: KouizNotificationTypeSealedWrapper, message: Message? = null, friendship: Friendship? = null): KouizNotification
    {

        return this.kouizNotificationRepository.save(KouizNotification(type, Date())
                .also {
                    it.message = message
                    it.friendShip = friendship
                    LOGGER.info("New notification created : ${it.id}")
                })
    }

    companion object
    {
        val LOGGER = LoggerFactory.getLogger(KouizNotificationService::class.java)
    }

}