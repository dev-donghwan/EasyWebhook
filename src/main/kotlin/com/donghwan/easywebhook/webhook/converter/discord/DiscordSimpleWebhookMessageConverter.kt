package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification

class DiscordSimpleWebhookMessageConverter : WebhookMessageConverter<SimpleWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.DISCORD
    override val notificationClass: Class<SimpleWebhookNotification> = SimpleWebhookNotification::class.java

    override fun convert(notification: SimpleWebhookNotification): Any {
        return mapOf(
            "embeds" to listOf(
                mapOf(
                    "title" to notification.title,
                    "description" to notification.content
                )
            )
        )
    }
}
