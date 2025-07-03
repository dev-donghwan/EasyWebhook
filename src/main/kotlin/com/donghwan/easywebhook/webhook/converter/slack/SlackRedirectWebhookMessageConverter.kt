package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification

class SlackRedirectWebhookMessageConverter : WebhookMessageConverter<RedirectWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.SLACK
    override val notificationClass: Class<RedirectWebhookNotification> = RedirectWebhookNotification::class.java

    override fun convert(notification: RedirectWebhookNotification): Any {
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
                ),
                mapOf(
                    "type" to "actions",
                    "elements" to listOf(
                        mapOf(
                            "type" to "button",
                            "text" to mapOf(
                                "type" to "plain_text",
                                "text" to "바로가기"
                            ),
                            "url" to notification.redirectUrl
                        )
                    )
                )
            )
        )
    }
}