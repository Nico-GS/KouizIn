package com.app.quiz.enums

import java.util.Locale

enum class Role
{

    DEFAULT,
    ADMIN;

    companion object
    {

        fun fromString(value: String): Role
        {
            return try
            {
                valueOf(value.uppercase(Locale.getDefault()))
            } catch (ex: IllegalArgumentException)
            {
                DEFAULT
            }
        }
    }
}