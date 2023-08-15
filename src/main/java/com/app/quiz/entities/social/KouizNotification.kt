package com.app.quiz.entities.social

import com.app.quiz.event.KouizNotificationTypeSealedWrapper
import com.app.quiz.event.enum.KouizNotificationType
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * This class is the representation of a [KouizNotification]
 */
@Document(collection = "kouiz_notification")
data class KouizNotification(
        var wrapper: KouizNotificationTypeSealedWrapper = KouizNotificationTypeSealedWrapper.DefaultNotificationType(),
        val creationDate: Date = Date()
)
{
    @Id
    var id: UUID = UUID.randomUUID()

    var type: KouizNotificationType = wrapper.kouizNotificationType

    var message: Message? = null
    var friendShip: Friendship? = null

    // region Postload wrapper

    /**
     * We need to initialize the alerts through [KouizNotificationTypeSealedWrapper]
     * with the correct parameters defined in [KouizNotificationTypeSealedWrapper] :
     * For now only [Message] and [Friendship]
     */
    @PostLoad
    protected fun postLoad()
    {
        this.wrapper = when (this.type)
        {
            KouizNotificationType.DEFAULT_NO_TYPE -> KouizNotificationTypeSealedWrapper.DefaultNotificationType()
            KouizNotificationType.NEW_MESSAGE -> KouizNotificationTypeSealedWrapper.NewMessageFromUser(this.message!!)
            KouizNotificationType.NEW_FRIEND_REQUEST -> KouizNotificationTypeSealedWrapper.NewFriendRequest(this.friendShip!!)
        }
    }

    // endregion

    // region Equals / Hashcode

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KouizNotification

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int
    {
        return id.hashCode()
    }

    // endregion
}