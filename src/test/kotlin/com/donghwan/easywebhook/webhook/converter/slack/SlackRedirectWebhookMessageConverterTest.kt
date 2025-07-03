package com.donghwan.easywebhook.webhook.converter.slack

import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SlackRedirectWebhookMessageConverterTest {

    private val converter = SlackRedirectWebhookMessageConverter()

    @Test
    fun convert_ShouldContainButtonBlock() {
        val notification = RedirectWebhookNotification("Title Slack", "Redirect content", "https://slack.com")
        val result = converter.convert(notification) as Map<*, *>

        assertTrue(result.containsKey("blocks"))
        val blocks = result["blocks"] as List<*>
        assertTrue(blocks.any { it.toString().contains("button") })
    }
}