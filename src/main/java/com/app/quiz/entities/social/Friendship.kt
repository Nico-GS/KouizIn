package com.app.quiz.entities.social

import com.app.quiz.entities.user.User
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "friendships")
data class Friendship(

        @DBRef
        var user: User = User(),

        @DBRef
        var friend: User = User(),

        var status: FriendshipStatus = FriendshipStatus.PENDING
)
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID = UUID.randomUUID()

    enum class FriendshipStatus
    {
        PENDING,
        ACCEPTED,
        REJECTED,
        CANCELED
    }
}