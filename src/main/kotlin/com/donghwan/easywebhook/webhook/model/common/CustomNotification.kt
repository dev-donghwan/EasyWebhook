package com.donghwan.easywebhook.webhook.model.common

import com.donghwan.easywebhook.webhook.model.WebhookNotification

interface CustomNotification : WebhookNotification {
    val data: Map<String, Any>
}

