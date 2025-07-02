package com.donghwan.easywebhook.webhook.model.slack

data class SlackRedirectNotification(
    val title: String,
    val content: String,
    val redirectUrl: String
) : SlackNotification