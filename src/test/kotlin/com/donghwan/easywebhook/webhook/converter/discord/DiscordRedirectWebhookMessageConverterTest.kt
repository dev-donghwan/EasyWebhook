package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DiscordRedirectWebhookMessageConverterTest {

    private val converter = DiscordRedirectWebhookMessageConverter()

    @Test
    fun convert_ShouldContainContentAndEmbedsWithUrl() {
        val notification = RedirectWebhookNotification("Discord Title", "Redirect message", "https://discord.com")
        val result = converter.convert(notification) as Map<*, *>

        assertTrue(result.containsKey("content"))
        assertTrue(result.containsKey("embeds"))

        val embeds = result["embeds"] as List<*>
        assertTrue(embeds.any { it.toString().contains("url=https://discord.com") || it.toString().contains("url") })
    }
}