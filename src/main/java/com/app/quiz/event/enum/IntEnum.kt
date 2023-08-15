package com.app.quiz.event.enum

/**
 * Interface that should be implemented by ALL our enums, if we want to store them as [Int]s in the DB
 */
interface IntEnum
{
    val intValue: Int
}