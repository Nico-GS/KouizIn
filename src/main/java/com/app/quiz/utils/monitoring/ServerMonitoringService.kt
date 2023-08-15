package com.app.quiz.utils.monitoring

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Service

/**
 * Utils class for monitoring our server though Discord
 */
@Service
class ServerMonitoringService : CoroutineScope, DisposableBean
{

    /**
     * Clean & destroy context
     */
    override fun destroy()
    {
        coroutineContext.cancel()
    }

    override val coroutineContext = Dispatchers.Default + SupervisorJob()

    private val token = "MTEzODg1MTcyMzE2MzcyOTk2MA.G_4aw3.iIZzhYI5nvrvRt6orQhkJFpfRh_sBcYUjXlgMU"
    private val channelId = "1138847076214644787"
    private val jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build()

    //    init
//    {
//        launch {
//            while (isActive)
//            {
//                reportServerStatus()
//                delay(600000) // 30 secondes
//            }
//        }
//    }
    init
    {
        jda.addEventListener(object : ListenerAdapter()
        {
            override fun onMessageReceived(event: MessageReceivedEvent)
            {
                if (event.message.contentRaw == "!statut")
                {
                    reportServerStatus(event.channel)
                }
            }
        })
        jda.awaitReady()
    }

    // region Private methods

    private fun reportServerStatus(channel: MessageChannel)
    {
        val isOnline: Boolean = isServerOnline()

        val statusEmoji = if (isOnline) "🟢" else "🔴"
        val statusMessage = if (isOnline) "Online" else "Offline"

        val message = buildString {
            append("État du serveur : $statusMessage $statusEmoji\n")
        }

        channel.sendMessage(message).queue()
    }

    private fun isServerOnline(): Boolean
    {
        val url = "https://backend.kouiz.in:8443"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response: Response = client.newCall(request).execute()
        return try
        {

            response.code == 404
        } catch (e: Exception)
        {
            false
        } finally
        {
            response.close()
        }
    }

    // endregion

}