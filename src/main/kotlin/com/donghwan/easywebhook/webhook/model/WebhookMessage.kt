package com.donghwan.easywebhook.webhook.model

data class WebhookMessage(
    val type: WebhookType,
    val url: String,
    val body: WebhookNotification
)