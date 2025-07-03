package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SlackCustomWebhookMessageConverterTest {

    private val converter = SlackCustomWebhookMessageConverter()

    @Test
    fun convert_ShouldReturnCustomText() {
        val notification = CustomWebhookNotification(mapOf("text" to "Custom Slack Message"))
        val result = converter.convert(notification) as Map<*, *>

        assertEquals("Custom Slack Message", result["text"])
    }
}