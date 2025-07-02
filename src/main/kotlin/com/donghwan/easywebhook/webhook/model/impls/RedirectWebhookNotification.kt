package com.donghwan.easywebhook.webhook.model.impls

import com.donghwan.easywebhook.webhook.model.WebhookNotification

data class RedirectWebhookNotification(
    val title: String,
    val content: String,
    val redirectUrl: String
) : WebhookNotification