package com.donghwan.easywebhook.webhook.converter.discord

import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DiscordCustomWebhookMessageConverterTest {

    private val converter = DiscordCustomWebhookMessageConverter()

    @Test
    fun convert_ShouldReturnSameData() {
        val data = mapOf("content" to "Custom Discord Message")
        val notification = CustomWebhookNotification(data)
        val result = converter.convert(notification) as Map<*, *>

        assertEquals(data, result)
    }
}