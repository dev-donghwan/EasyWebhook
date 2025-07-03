package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SlackSimpleWebhookMessageConverterTest {

    private val converter = SlackSimpleWebhookMessageConverter()

    @Test
    fun convert_ShouldReturnSlackBlocks() {
        val notification = SimpleWebhookNotification("Hello Slack", "This is a simple message.")
        val result = converter.convert(notification) as Map<*, *>

        assertTrue(result.containsKey("blocks"))
        val blocks = result["blocks"] as List<*>
        assertTrue(blocks.isNotEmpty())
    }
}