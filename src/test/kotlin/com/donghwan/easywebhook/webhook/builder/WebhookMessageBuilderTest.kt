package com.donghwan.easywebhook.webhook.builder

import com.donghwan.easywebhook.webhook.model.WebhookType
import com.donghwan.easywebhook.webhook.model.common.CommonCustomNotification
import com.donghwan.easywebhook.webhook.model.discord.DiscordRedirectNotification
import com.donghwan.easywebhook.webhook.model.slack.SlackSimpleNotification
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WebhookMessageBuilderTest {

    @Test
    fun `type setting returns builder`() {
        val builder = WebhookMessageBuilder()
        val returned = builder.type(WebhookType.SLACK)
        assertSame(builder, returned)
    }

    @Test
    fun `url setting returns builder`() {
        val builder = WebhookMessageBuilder()
        val returned = builder.url("https://example.com")
        assertSame(builder, returned)
    }

    @Test
    fun `build throws if type not set`() {
        val builder = WebhookMessageBuilder()
        builder.url("https://example.com")

        val ex = assertThrows<IllegalStateException> {
            builder.simple {
                title = "Title"
                content = "Content"
            }
        }
        assertEquals("Type must be set before calling simple", ex.message)
    }

    @Test
    fun `build throws if url not set`() {
        val builder = WebhookMessageBuilder()
        builder.type(WebhookType.SLACK)
        builder.simple {
            title = "Title"
            content = "Content"
        }
        val ex = assertThrows<IllegalStateException> { builder.build() }
        assertEquals("URL must be set", ex.message)
    }

    @Test
    fun `simple notification builds correctly`() {
        val builder = WebhookMessageBuilder()
        builder.type(WebhookType.SLACK)
            .url("https://slack.url")
            .simple {
                title = "Hello"
                content = "World"
            }
        val message = builder.build()
        assertEquals(WebhookType.SLACK, message.type)
        assertEquals("https://slack.url", message.url)
        assertTrue(message.body is SlackSimpleNotification)
        val body = message.body as SlackSimpleNotification
        assertEquals("Hello", body.title)
        assertEquals("World", body.content)
    }

    @Test
    fun `simple throws if type not set`() {
        val builder = WebhookMessageBuilder()
        val ex = assertThrows<IllegalStateException> {
            builder.simple {
                title = "Hi"
                content = "Test"
            }
        }
        assertEquals("Type must be set before calling simple", ex.message)
    }

    @Test
    fun `redirect notification builds correctly`() {
        val builder = WebhookMessageBuilder()
        builder.type(WebhookType.DISCORD)
            .url("https://discord.url")
            .redirect {
                title = "Redirect"
                content = "Click"
                redirectUrl = "https://redirect.url"
            }
        val message = builder.build()
        assertEquals(WebhookType.DISCORD, message.type)
        assertEquals("https://discord.url", message.url)
        assertTrue(message.body is DiscordRedirectNotification)
        val body = message.body as DiscordRedirectNotification
        assertEquals("Redirect", body.title)
        assertEquals("Click", body.content)
        assertEquals("https://redirect.url", body.redirectUrl)
    }

    @Test
    fun `redirect throws if type not set`() {
        val builder = WebhookMessageBuilder()
        val ex = assertThrows<IllegalStateException> {
            builder.redirect {
                title = "Redirect"
                content = "Click"
                redirectUrl = "https://redirect.url"
            }
        }
        assertEquals("Type must be set before calling redirect", ex.message)
    }

    @Test
    fun `custom notification builds correctly`() {
        val builder = WebhookMessageBuilder()
        builder.type(WebhookType.SLACK)
            .url("https://slack.url")
            .custom {
                field("foo", 123)
                child("bar") {
                    field("baz", "qux")
                }
            }
        val message = builder.build()
        assertEquals(WebhookType.SLACK, message.type)
        assertEquals("https://slack.url", message.url)
        assertTrue(message.body is CommonCustomNotification)
        val body = message.body as CommonCustomNotification
        assertEquals(123, body.data["foo"])
        val nested = body.data["bar"] as? Map<*, *>
        assertNotNull(nested)
        assertEquals("qux", nested?.get("baz"))
    }

    @Test
    fun `custom throws if type not set`() {
        val builder = WebhookMessageBuilder()
        val ex = assertThrows<IllegalStateException> {
            builder.custom {
                field("foo", 1)
            }
        }
        assertEquals("Type must be set before calling custom", ex.message)
    }

    @Test
    fun `build throws if body not set`() {
        val builder = WebhookMessageBuilder()
        builder.type(WebhookType.SLACK)
            .url("https://slack.url")

        val ex = assertThrows<IllegalStateException> {
            builder.build()
        }
        assertEquals("Body must be set", ex.message)
    }
}