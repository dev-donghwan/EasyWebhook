package com.donghwan.easywebhook.webhook.builder

import com.donghwan.easywebhook.webhook.model.common.CommonCustomNotification
import com.donghwan.easywebhook.webhook.model.discord.DiscordRedirectNotification
import com.donghwan.easywebhook.webhook.model.discord.DiscordSimpleNotification
import com.donghwan.easywebhook.webhook.model.slack.SlackRedirectNotification
import com.donghwan.easywebhook.webhook.model.slack.SlackSimpleNotification
import com.donghwan.easywebhook.webhook.model.WebhookMessage
import com.donghwan.easywebhook.webhook.model.WebhookNotification
import com.donghwan.easywebhook.webhook.model.WebhookType

class WebhookMessageBuilder {

    private var type: WebhookType? = null
    private var url: String? = null
    private var body: WebhookNotification? = null

    fun type(type: WebhookType): WebhookMessageBuilder {
        this.type = type
        return this
    }

    fun url(url: String): WebhookMessageBuilder {
        this.url = url
        return this
    }

    fun simple(block: SimpleNotificationBuilder.() -> Unit): WebhookMessageBuilder {
        checkTypeSupportedFor("simple")
        val builder = SimpleNotificationBuilder().apply(block)
        val (title, content) = builder.build()

        body = when (type) {
            WebhookType.SLACK -> SlackSimpleNotification(title, content)
            WebhookType.DISCORD -> DiscordSimpleNotification(title, content)
            else -> throw IllegalStateException("Unsupported type for simpleNotification")
        }
        return this
    }

    fun redirect(block: RedirectNotificationBuilder.() -> Unit): WebhookMessageBuilder {
        checkTypeSupportedFor("redirect")
        val builder = RedirectNotificationBuilder().apply(block)
        val (title, content, redirectUrl) = builder.build()

        body = when (type) {
            WebhookType.SLACK -> SlackRedirectNotification(title, content, redirectUrl)
            WebhookType.DISCORD -> DiscordRedirectNotification(title, content, redirectUrl)
            else -> throw IllegalStateException("Unsupported type for redirectNotification")
        }
        return this
    }

    fun custom(block: CustomDataBuilder.() -> Unit): WebhookMessageBuilder {
        checkTypeSupportedFor("custom")
        val builder = CustomDataBuilder()
        builder.block()
        body = CommonCustomNotification(builder.build())
        return this
    }

    private fun checkTypeSupportedFor(functionName: String) {
        val currentType = type ?: throw IllegalStateException("Type must be set before calling $functionName")
        if (currentType !in listOf(WebhookType.SLACK, WebhookType.DISCORD)) {
            throw IllegalStateException("$functionName is not supported for type $currentType")
        }
    }

    fun build(): WebhookMessage {
        val currentType = type ?: throw IllegalStateException("Type must be set")
        val currentUrl = url ?: throw IllegalStateException("URL must be set")
        val currentBody = body ?: throw IllegalStateException("Body must be set")

        return WebhookMessage(currentType, currentUrl, currentBody)
    }
}