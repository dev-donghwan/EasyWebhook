package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification

class SlackSimpleWebhookMessageConverter : WebhookMessageConverter<SimpleWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.SLACK
    override val notificationClass: Class<SimpleWebhookNotification> = SimpleWebhookNotification::class.java

    override fun convert(notification: SimpleWebhookNotification): Any {
        return mapOf(
            "blocks" to listOf(
                mapOf(
                    "type" to "header",
                    "text" to mapOf(
                        "type" to "plain_text",
                        "text" to notification.title
                    )
                ),
                mapOf(
                    "type" to "section",
                    "text" to mapOf(
                        "type" to "mrkdwn",
                        "text" to notification.content
                    )
                )
            )
        )
    }
}