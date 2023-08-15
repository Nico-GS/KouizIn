package com.app.quiz.repositories.social

import com.app.quiz.entities.social.Friendship
import com.app.quiz.entities.user.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FriendshipRepository : MongoRepository<Friendship, UUID>
{
    fun findByUserAndFriend(user: User, friend: User): Friendship?
    fun findAllByUserAndStatus(user: User, status: Friendship.FriendshipStatus): List<Friendship>
}