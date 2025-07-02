package com.donghwan.easywebhook.webhook.model.discord

data class DiscordSimpleNotification(
    val title: String,
    val content: String
) : DiscordNotification

