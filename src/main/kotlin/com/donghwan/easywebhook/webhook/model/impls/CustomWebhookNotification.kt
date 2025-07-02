package com.donghwan.easywebhook.webhook.model.impls

import com.donghwan.easywebhook.webhook.model.WebhookNotification

data class CustomWebhookNotification(
    val data: Map<String, Any>
) : WebhookNotification