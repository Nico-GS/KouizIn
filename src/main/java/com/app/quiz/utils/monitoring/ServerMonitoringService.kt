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
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Utils class for monitoring our server though Discord
 */
@Service
class ServerMonitoringService : CoroutineScope, DisposableBean
{

    // region lateinit

    @Value("\${token.discord-bot}")
    private var TOKEN_DISCORD_BOT: String = ""

    // endregion

    /**
     * Clean & destroy context
     */
    override fun destroy()
    {
        coroutineContext.cancel()
    }

    override val coroutineContext = Dispatchers.Default + SupervisorJob()
    private val jda = JDABuilder.createDefault(TOKEN_DISCORD_BOT).enableIntents(GatewayIntent.MESSAGE_CONTENT).build()

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

        val client = OkHttpClient()
        val request = Request.Builder().url(URL).build()
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

    companion object
    {
        //        @Value("\${token.discord-bot}")
//        private lateinit var TOKEN_DISCORD_BOT: String
        private const val URL = "https://backend.kouiz.in:8443"
    }


}