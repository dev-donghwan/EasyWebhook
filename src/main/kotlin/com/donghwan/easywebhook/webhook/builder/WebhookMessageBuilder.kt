package com.donghwan.easywebhook.webhook.builder

import com.donghwan.easywebhook.webhook.WebhookMessage
import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.model.WebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification

class WebhookMessageBuilder {
    private var type: WebhookType? = null
    private var url: String? = null
    private var body: WebhookNotification? = null

    fun type(type: WebhookType) = apply { this.type = type }
    fun url(url: String) = apply { this.url = url }

    fun simple(block: SimpleNotificationBuilder.() -> Unit) = apply {
        val builder = SimpleNotificationBuilder().apply(block)
        body = SimpleWebhookNotification(builder.title, builder.content)
    }

    fun redirect(block: RedirectNotificationBuilder.() -> Unit) = apply {
        val builder = RedirectNotificationBuilder().apply(block)
        body = RedirectWebhookNotification(builder.title, builder.content, builder.redirectUrl)
    }

    fun custom(block: CustomNotificationBuilder.() -> Unit) = apply {
        val builder = CustomNotificationBuilder().apply(block)
        body = CustomWebhookNotification(builder.build())
    }

    fun build(): WebhookMessage {
        val currentType = type ?: throw IllegalStateException("Type must be set")
        val currentUrl = url ?: throw IllegalStateException("URL must be set")
        val currentBody = body ?: throw IllegalStateException("Body must be set")
        return WebhookMessage(currentType, currentUrl, currentBody)
    }
}