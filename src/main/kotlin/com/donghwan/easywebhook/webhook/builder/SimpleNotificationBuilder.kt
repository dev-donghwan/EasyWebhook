package com.donghwan.easywebhook.webhook.builder

class SimpleNotificationBuilder {
    var title: String = ""
    var content: String = ""

    fun build() = Pair(title, content)
}