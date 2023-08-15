package com.app.quiz

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableCaching
@Async
open class QuizApplication
{

    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            SpringApplication.run(QuizApplication::class.java, *args)
        }
    }
}