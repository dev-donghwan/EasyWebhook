package com.donghwan.easywebhook.webhook.builder

class CustomNotificationBuilder {
    private val data = mutableMapOf<String, Any>()

    fun field(key: String, value: Any) {
        data[key] = value
    }

    fun child(key: String, block: CustomNotificationBuilder.() -> Unit) {
        val nestedBuilder = CustomNotificationBuilder()
        nestedBuilder.block()
        data[key] = nestedBuilder.build()
    }

    fun build(): Map<String, Any> = data
}