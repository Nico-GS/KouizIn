package com.app.quiz.event.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Component
class KouizEventPublisher
{

    // region Autowired

    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    // endregion

    fun publish(event: ApplicationEvent)
    {
        this.publisher.publishEvent(event)
    }

    fun publishAfterCommit(event: ApplicationEvent)
    {
        this.afterCommit { this.publish(event) }
    }

    // region private methods

    private fun afterCommit(block: () -> Unit)
    {
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization
        {
            override fun afterCommit()
            {
                block()
            }
        })
    }

    // endregion

}