package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DiscordSimpleWebhookMessageConverterTest {

    private val converter = DiscordSimpleWebhookMessageConverter()

    @Test
    fun convert_ShouldReturnEmbeds() {
        val notification = SimpleWebhookNotification("Hello Discord", "Simple discord message")
        val result = converter.convert(notification) as Map<*, *>

        assertTrue(result.containsKey("embeds"))
        val embeds = result["embeds"] as List<*>
        assertTrue(embeds.isNotEmpty())
    }
}