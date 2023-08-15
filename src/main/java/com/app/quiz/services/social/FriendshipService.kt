package com.app.quiz.services.social

import com.app.quiz.entities.social.Friendship
import com.app.quiz.event.NewFriendshipRequestEvent
import com.app.quiz.event.config.KouizEventPublisher
import com.app.quiz.exceptions.social.FriendshipAlreadyExistsException
import com.app.quiz.exceptions.user.UserNotFoundException
import com.app.quiz.repositories.social.FriendshipRepository
import com.app.quiz.repositories.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FriendshipService
{

    // region Autowired

    @Autowired
    private lateinit var friendshipRepository: FriendshipRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var publisher: KouizEventPublisher

    // endregion

    fun addFriend(userRequestId: Long, friendId: Long)
    {

        val user = this.userRepository.findByIdOrNull(userRequestId)
            ?: throw UserNotFoundException("")

        /**
         * Find the friend to add
         */
        val friend = userRepository.findByIdOrNull(friendId) ?: throw UserNotFoundException("Friend not found")

        if (user.id == friend.id)
        {
            throw FriendshipAlreadyExistsException("Cannot add yourself as a friend")
        }
        if (user.friendships.any { it.friend.id == friend.id })
        {
            throw FriendshipAlreadyExistsException("Friendship already exists")
        }

        val friendship = Friendship(user = user, friend = friend, status = Friendship.FriendshipStatus.PENDING)
        this.friendshipRepository.save(friendship)

        user.friendships.add(friendship)
        this.userRepository.save(user)

        /**
         * Create a [NewFriendshipRequestEvent]
         */
        this.publisher.publish(NewFriendshipRequestEvent(friendship))
    }

    companion object
    {
        val LOGGER = LoggerFactory.getLogger(FriendshipService::class.java)
    }

}