package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification

class DiscordCustomWebhookMessageConverter : WebhookMessageConverter<CustomWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.DISCORD
    override val notificationClass: Class<CustomWebhookNotification> = CustomWebhookNotification::class.java

    override fun convert(notification: CustomWebhookNotification): Any {
        return notification.data
    }
}