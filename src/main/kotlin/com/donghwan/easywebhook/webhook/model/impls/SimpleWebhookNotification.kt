package com.donghwan.easywebhook.webhook.model.impls

import com.donghwan.easywebhook.webhook.model.WebhookNotification

data class SimpleWebhookNotification(
    val title: String,
    val content: String
) : WebhookNotification