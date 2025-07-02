package com.donghwan.easywebhook.webhook.builder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RedirectNotificationBuilderTest {

    @Test
    fun `fields are set correctly and build returns correct triple`() {
        val builder = RedirectNotificationBuilder().apply {
            title = "Redirect Title"
            content = "Redirect Content"
            redirectUrl = "https://redirect.url"
        }

        val (title, content, redirectUrl) = builder.build()

        assertEquals("Redirect Title", title)
        assertEquals("Redirect Content", content)
        assertEquals("https://redirect.url", redirectUrl)
    }

    @Test
    fun `default fields are empty strings`() {
        val builder = RedirectNotificationBuilder()
        val (title, content, redirectUrl) = builder.build()

        assertEquals("", title)
        assertEquals("", content)
        assertEquals("", redirectUrl)
    }

    @Test
    fun `can overwrite fields`() {
        val builder = RedirectNotificationBuilder().apply {
            title = "First"
            title = "Second"
            content = "Content"
            redirectUrl = "url1"
            redirectUrl = "url2"
        }
        val (title, content, redirectUrl) = builder.build()

        assertEquals("Second", title)
        assertEquals("Content", content)
        assertEquals("url2", redirectUrl)
    }
}