package com.donghwan.easywebhook.webhook.builder

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.model.impls.CustomWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.RedirectWebhookNotification
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WebhookMessageBuilderTest {

    @Nested
    inner class TypeAndUrlTests {

        @Test
        fun `setting type returns builder instance`() {
            val builder = WebhookMessageBuilder()
            val result = builder.type(WebhookType.SLACK)
            assertSame(builder, result)
        }

        @Test
        fun `setting url returns builder instance`() {
            val builder = WebhookMessageBuilder()
            val result = builder.url("https://example.com")
            assertSame(builder, result)
        }
    }

    @Nested
    inner class SimpleNotificationTests {

        @Test
        fun `build simple notification for slack`() {
            val builder = WebhookMessageBuilder()
                .type(WebhookType.SLACK)
                .url("https://slack.example.com")
                .simple {
                    title = "Hello Slack"
                    content = "Simple message content"
                }
            val message = builder.build()

            assertEquals(WebhookType.SLACK, message.type)
            assertEquals("https://slack.example.com", message.url)
            assertTrue(message.body is SimpleWebhookNotification)

            val body = message.body as SimpleWebhookNotification
            assertEquals("Hello Slack", body.title)
            assertEquals("Simple message content", body.content)
        }
    }

    @Nested
    inner class RedirectNotificationTests {

        @Test
        fun `build redirect notification for discord`() {
            val builder = WebhookMessageBuilder()
                .type(WebhookType.DISCORD)
                .url("https://discord.example.com")
                .redirect {
                    title = "Redirect Title"
                    content = "Redirect content"
                    redirectUrl = "https://redirect.url"
                }
            val message = builder.build()

            assertEquals(WebhookType.DISCORD, message.type)
            assertEquals("https://discord.example.com", message.url)
            assertTrue(message.body is RedirectWebhookNotification)

            val body = message.body as RedirectWebhookNotification
            assertEquals("Redirect Title", body.title)
            assertEquals("Redirect content", body.content)
            assertEquals("https://redirect.url", body.redirectUrl)
        }
    }

    @Nested
    inner class CustomNotificationTests {

        @Test
        fun `build custom notification for slack`() {
            val builder = WebhookMessageBuilder()
                .type(WebhookType.SLACK)
                .url("https://slack.example.com")
                .custom {
                    field("customKey", "customValue")
                    child("nested") {
                        field("nestedKey", 123)
                    }
                }
            val message = builder.build()

            assertEquals(WebhookType.SLACK, message.type)
            assertEquals("https://slack.example.com", message.url)
            assertTrue(message.body is CustomWebhookNotification)

            val body = message.body as CustomWebhookNotification
            assertEquals("customValue", body.data["customKey"])
            val nested = body.data["nested"] as? Map<*, *>
            assertNotNull(nested)
            assertEquals(123, nested?.get("nestedKey"))
        }
    }

    @Nested
    inner class BuildValidationTests {

        @Test
        fun `build fails if type not set`() {
            val builder = WebhookMessageBuilder()
                .url("https://example.com")
                .simple {
                    title = "No Type"
                    content = "Missing type"
                }
            val ex = assertThrows<IllegalStateException> { builder.build() }
            assertEquals("Type must be set", ex.message)
        }

        @Test
        fun `build fails if url not set`() {
            val builder = WebhookMessageBuilder()
                .type(WebhookType.SLACK)
                .simple {
                    title = "No URL"
                    content = "Missing url"
                }
            val ex = assertThrows<IllegalStateException> { builder.build() }
            assertEquals("URL must be set", ex.message)
        }

        @Test
        fun `build fails if body not set`() {
            val builder = WebhookMessageBuilder()
                .type(WebhookType.SLACK)
                .url("https://example.com")
            val ex = assertThrows<IllegalStateException> { builder.build() }
            assertEquals("Body must be set", ex.message)
        }
    }
}