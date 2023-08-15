package com.app.quiz.event.enum


/**
 * Here we have our []
 */
enum class KouizNotificationType(override val intValue: Int) : IntEnum
{
    DEFAULT_NO_TYPE(0),
    NEW_MESSAGE(1),
    NEW_FRIEND_REQUEST(2)
}


