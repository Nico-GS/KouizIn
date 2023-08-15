package com.app.quiz.event

import com.app.quiz.entities.social.Friendship
import org.springframework.context.ApplicationEvent

class NewFriendshipRequestEvent(val friendship: Friendship): ApplicationEvent(friendship)