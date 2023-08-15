package com.app.quiz.event

import com.app.quiz.entities.social.Friendship
import com.app.quiz.entities.social.Message
import com.app.quiz.event.enum.KouizNotificationType

/**
 * This class contains all the [KouizNotification] with their parameters
 * A [KouizNotification] can contain zero, one or multiples parameters,
 * but these parameters are defined in this class and only in this class
 */
sealed class KouizNotificationTypeSealedWrapper(val kouizNotificationType: KouizNotificationType, val parameters: List<String> = listOf())
{

    /**
     * Default notification
     */
    class DefaultNotificationType :
            KouizNotificationTypeSealedWrapper(KouizNotificationType.DEFAULT_NO_TYPE)

    /**
     * New message
     */
    class NewMessageFromUser(message: Message) :
            KouizNotificationTypeSealedWrapper(KouizNotificationType.NEW_MESSAGE, listOf(message.receiverId.toString()))

    /**
     * New friend request
     */
    class NewFriendRequest(friendRequest: Friendship) :
            KouizNotificationTypeSealedWrapper(KouizNotificationType.NEW_FRIEND_REQUEST, listOf(friendRequest.friend.pseudo
                    ?: friendRequest.friend.email))

}