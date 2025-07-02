package com.donghwan.easywebhook.webhook.builder

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CustomNotificationBuilderTest {

    @Test
    fun `field adds single key-value pair`() {
        val builder = CustomNotificationBuilder()
        builder.field("key1", "value1")

        val result = builder.build()
        assertEquals(1, result.size)
        assertEquals("value1", result["key1"])
    }

    @Test
    fun `child creates nested map correctly`() {
        val builder = CustomNotificationBuilder()
        builder.child("parent") {
            field("childKey", 123)
            field("anotherKey", true)
        }

        val result = builder.build()
        assertTrue(result.containsKey("parent"))
        val nested = result["parent"] as? Map<*, *>
        assertNotNull(nested)
        assertEquals(123, nested?.get("childKey"))
        assertEquals(true, nested?.get("anotherKey"))
    }

    @Test
    fun `multiple fields and child calls combined`() {
        val builder = CustomNotificationBuilder()
        builder.field("simpleKey", "simpleValue")
        builder.child("child1") {
            field("nestedKey1", 10)
            child("child2") {
                field("deepKey", "deepValue")
            }
        }
        builder.field("anotherKey", 3.14)

        val result = builder.build()
        assertEquals("simpleValue", result["simpleKey"])
        assertEquals(3.14, result["anotherKey"])

        val child1 = result["child1"] as? Map<*, *>
        assertNotNull(child1)
        assertEquals(10, child1?.get("nestedKey1"))

        val child2 = child1?.get("child2") as? Map<*, *>
        assertNotNull(child2)
        assertEquals("deepValue", child2?.get("deepKey"))
    }

    @Test
    fun `empty builder produces empty map`() {
        val builder = CustomNotificationBuilder()
        val result = builder.build()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `child can be called multiple times for different keys`() {
        val builder = CustomNotificationBuilder()
        builder.child("first") {
            field("a", 1)
        }
        builder.child("second") {
            field("b", 2)
        }

        val result = builder.build()
        assertEquals(1, (result["first"] as? Map<*, *>)?.get("a"))
        assertEquals(2, (result["second"] as? Map<*, *>)?.get("b"))
    }

    @Test
    fun `overwriting keys replaces previous value`() {
        val builder = CustomNotificationBuilder()
        builder.field("key", "firstValue")
        builder.field("key", "secondValue")

        val result = builder.build()
        assertEquals("secondValue", result["key"])
    }

    @Test
    fun `complex nested structure with multiple levels`() {
        val builder = CustomNotificationBuilder()
        builder.field("level1Key", "value1")
        builder.child("level1Child") {
            field("level2Key", 42)
            child("level2Child") {
                field("level3Key", true)
                child("level3Child") {
                    field("level4Key", 3.1415)
                }
            }
        }

        val result = builder.build()
        assertEquals("value1", result["level1Key"])

        val level1Child = result["level1Child"] as? Map<*, *>
        assertNotNull(level1Child)
        assertEquals(42, level1Child?.get("level2Key"))

        val level2Child = level1Child?.get("level2Child") as? Map<*, *>
        assertNotNull(level2Child)
        assertEquals(true, level2Child?.get("level3Key"))

        val level3Child = level2Child?.get("level3Child") as? Map<*, *>
        assertNotNull(level3Child)
        assertEquals(3.1415, level3Child?.get("level4Key"))
    }
}