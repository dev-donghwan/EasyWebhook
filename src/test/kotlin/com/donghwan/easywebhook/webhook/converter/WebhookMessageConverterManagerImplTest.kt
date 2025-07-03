package com.donghwan.easywebhook.webhook.converter

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.converter.discord.DiscordCustomWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.discord.DiscordRedirectWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.discord.DiscordSimpleWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackCustomWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackRedirectWebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.slack.SlackSimpleWebhookMessageConverter
import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WebhookMessageConverterManagerImplTest {

    private val manager = WebhookMessageConverterManagerImpl()

    private val slackType = WebhookType.SLACK
    private val discordType = WebhookType.DISCORD

    @Test
    fun findConverter_ShouldFindAllSlackConverters() {
        val simple = SimpleWebhookNotification("title", "content")
        val custom = CustomWebhookNotification(mapOf("key" to "value"))
        val redirect = RedirectWebhookNotification("title", "content", "https://url")

        val simpleConverter = manager.findConverter(slackType, simple)
        assertTrue(simpleConverter is SlackSimpleWebhookMessageConverter)

        val customConverter = manager.findConverter(slackType, custom)
        assertTrue(customConverter is SlackCustomWebhookMessageConverter)

        val redirectConverter = manager.findConverter(slackType, redirect)
        assertTrue(redirectConverter is SlackRedirectWebhookMessageConverter)
    }

    @Test
    fun findConverter_ShouldFindAllDiscordConverters() {
        val simple = SimpleWebhookNotification("title", "content")
        val custom = CustomWebhookNotification(mapOf("key" to "value"))
        val redirect = RedirectWebhookNotification("title", "content", "https://url")

        val simpleConverter = manager.findConverter(discordType, simple)
        assertTrue(simpleConverter is DiscordSimpleWebhookMessageConverter)

        val customConverter = manager.findConverter(discordType, custom)
        assertTrue(customConverter is DiscordCustomWebhookMessageConverter)

        val redirectConverter = manager.findConverter(discordType, redirect)
        assertTrue(redirectConverter is DiscordRedirectWebhookMessageConverter)
    }

    @Test
    fun findConverter_ShouldThrowForUnsupportedCombination() {
        val unknownNotification = object : com.donghwan.easywebhook.webhook.model.WebhookNotification {}

        val exception = assertThrows<IllegalArgumentException> {
            manager.findConverter(slackType, unknownNotification)
        }
        assertTrue(exception.message!!.contains("No converter found"))
    }
}