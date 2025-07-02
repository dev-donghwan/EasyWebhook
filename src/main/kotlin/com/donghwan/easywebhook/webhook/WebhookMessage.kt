package com.donghwan.easywebhook.webhook

import com.donghwan.easywebhook.webhook.model.WebhookNotification

data class WebhookMessage(
    val type: WebhookType,
    val url: String,
    val body: WebhookNotification
)