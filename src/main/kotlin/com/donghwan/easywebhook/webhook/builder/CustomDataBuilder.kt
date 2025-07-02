package com.donghwan.easywebhook.webhook.builder

class CustomDataBuilder {
    private val data = mutableMapOf<String, Any>()

    fun field(key: String, value: Any) {
        data[key] = value
    }

    fun child(key: String, block: CustomDataBuilder.() -> Unit) {
        val nestedBuilder = CustomDataBuilder()
        nestedBuilder.block()
        data[key] = nestedBuilder.build()
    }

    fun build(): Map<String, Any> = data
}