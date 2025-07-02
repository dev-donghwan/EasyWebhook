package com.donghwan.easywebhook.webhook.builder

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimpleNotificationBuilderTest {

    @Test
    fun `fields are set correctly and build returns correct pair`() {
        val builder = SimpleNotificationBuilder().apply {
            title = "Test Title"
            content = "Test Content"
        }

        val (title, content) = builder.build()

        assertEquals("Test Title", title)
        assertEquals("Test Content", content)
    }

    @Test
    fun `default fields are empty strings`() {
        val builder = SimpleNotificationBuilder()
        val (title, content) = builder.build()

        assertEquals("", title)
        assertEquals("", content)
    }

    @Test
    fun `can overwrite fields`() {
        val builder = SimpleNotificationBuilder().apply {
            title = "First"
            title = "Second"
            content = "Content"
        }
        val (title, content) = builder.build()

        assertEquals("Second", title)
        assertEquals("Content", content)
    }
}