package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification

class SlackCustomWebhookMessageConverter : WebhookMessageConverter<CustomWebhookNotification> {
    override val webhookType: WebhookType = WebhookType.SLACK
    override val notificationClass: Class<CustomWebhookNotification> = CustomWebhookNotification::class.java

    override fun convert(notification: CustomWebhookNotification): Any {
        return notification.data
    }
}