package com.donghwan.easywebhook.webhook.model.slack

data class SlackSimpleNotification(
    val title: String,
    val content: String
) : SlackNotification