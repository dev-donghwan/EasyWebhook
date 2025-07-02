package com.donghwan.easywebhook.webhook.model.discord

data class DiscordRedirectNotification(
    val title: String,
    val content: String,
    val redirectUrl: String
) : DiscordNotification