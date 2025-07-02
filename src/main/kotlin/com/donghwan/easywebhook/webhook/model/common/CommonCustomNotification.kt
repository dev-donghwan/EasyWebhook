package com.donghwan.easywebhook.webhook.model.common

data class CommonCustomNotification(
    override val data: Map<String, Any>
) : CustomNotification