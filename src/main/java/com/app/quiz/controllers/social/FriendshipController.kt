package com.app.quiz.controllers.social

import com.app.quiz.dto.FriendRequestDto
import com.app.quiz.services.social.FriendshipService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/social/friend")
class FriendshipController
{

    // region Autowired

    @Autowired
    private lateinit var friendshipService: FriendshipService

    // endregion

    @PostMapping("/add")
    fun addFriend(@RequestBody friendRequest: FriendRequestDto)
    {
        this.friendshipService.addFriend(friendRequest.userRequestId, friendRequest.friendId)
    }

}