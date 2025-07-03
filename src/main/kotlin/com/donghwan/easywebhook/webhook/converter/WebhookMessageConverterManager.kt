package com.donghwan.easywebhook.webhook.converter

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.discord.DiscordCustomWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.discord.DiscordRedirectWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.discord.DiscordSimpleWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackCustomWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackRedirectWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackSimpleWebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.WebhookNotification

interface WebhookMessageConverterManager {

    /**
     * 등록된 변환기들 중에서, 주어진 [type]과 [notification]을 처리할 수 있는 변환기를 찾아 반환합니다.
     *
     * @throws IllegalArgumentException 변환기를 찾지 못한 경우
     */
    fun findConverter(type: WebhookType, notification: WebhookNotification): WebhookMessageConverter<out WebhookNotification>
}

class WebhookMessageConverterManagerImpl : WebhookMessageConverterManager {

    private val converters: List<WebhookMessageConverter<out WebhookNotification>> = listOf(
        // Discord converters
        DiscordCustomWebhookMessageConverter(),
        DiscordSimpleWebhookMessageConverter(),
        DiscordRedirectWebhookMessageConverter(),

        // Slack converters
        SlackCustomWebhookMessageConverter(),
        SlackSimpleWebhookMessageConverter(),
        SlackRedirectWebhookMessageConverter()
    )

    override fun findConverter(
        type: WebhookType,
        notification: WebhookNotification
    ): WebhookMessageConverter<out WebhookNotification> {
        return converters.firstOrNull { it.supports(type, notification) }
            ?: throw IllegalArgumentException("No converter found for type: $type, notification: ${notification::class.simpleName}")
    }
}