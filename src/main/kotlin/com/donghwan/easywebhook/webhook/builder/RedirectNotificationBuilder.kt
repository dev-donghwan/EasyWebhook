package com.donghwan.easywebhook.webhook.builder

class RedirectNotificationBuilder {
    var title: String = ""
    var content: String = ""
    var redirectUrl: String = ""

    fun build() = Triple(title, content, redirectUrl)
}