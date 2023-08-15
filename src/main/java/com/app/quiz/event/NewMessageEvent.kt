package com.app.quiz.event

import com.app.quiz.entities.social.Message
import org.springframework.context.ApplicationEvent

class NewMessageEvent(val message: Message) : ApplicationEvent(message)