package com.app.quiz.services.social

import com.app.quiz.event.KouizNotificationTypeSealedWrapper
import com.app.quiz.event.NewFriendshipRequestEvent
import com.app.quiz.event.NewMessageEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

/**
 * This class is used for create new [KouizNotification]
 */
@Service
open class KouizNotificationCreatorService
{

    // region Autowired

    @Autowired
    private lateinit var kouizNotificationService: KouizNotificationService

    // endregion

    /**
     * Create a new [KouizNotification] when a user receive a new [Message]
     */
    @Async
    @EventListener
    open fun handleNewMessageEvent(event: NewMessageEvent)
    {
        this.kouizNotificationService.create(KouizNotificationTypeSealedWrapper.NewMessageFromUser(event.message))
    }

    /**
     * Create a new [KouizNotification] when a user receive a new [Friendship] request
     */
    @Async
    @EventListener
    open fun handleNewFriendshipRequestEvent(event: NewFriendshipRequestEvent)
    {
        this.kouizNotificationService.create(KouizNotificationTypeSealedWrapper.NewFriendRequest(event.friendship))
    }

    companion object
    {
        val LOGGER = LoggerFactory.getLogger(KouizNotificationCreatorService::class.java)
    }

}