package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification

class DiscordRedirectWebhookMessageConverter : WebhookMessageConverter<RedirectWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.DISCORD
    override val notificationClass: Class<RedirectWebhookNotification> = RedirectWebhookNotification::class.java

    override fun convert(notification: RedirectWebhookNotification): Any {
        return mapOf(
            "content" to "${notification.title}: ${notification.content}",
            "embeds" to listOf(
                mapOf(
                    "title" to notification.title,
                    "description" to notification.content,
                    "url" to notification.redirectUrl
                )
            )
        )
    }
}