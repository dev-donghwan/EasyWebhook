package com.donghwan.easywebhook.webhook.client

import com.donghwan.easywebhook.webhook.WebhookMessage

interface WebhookClient {
    fun sendMessage(message: WebhookMessage)
}