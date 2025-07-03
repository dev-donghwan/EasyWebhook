package com.donghwan.easywebhook.webhook.converter

import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.model.WebhookNotification

interface WebhookMessageConverter<T : WebhookNotification> {
    val webhookType: WebhookType
    val notificationClass: Class<T>

    /**
     * 해당 컨버터가 이 [type]과 [notification] 조합을 처리할 수 있는지 판단
     */
    fun supports(type: WebhookType, notification: WebhookNotification): Boolean {
        return type == webhookType && notificationClass.isInstance(notification)
    }

    /**
     * [notification] 객체를 플랫폼에 맞는 전송용 데이터로 변환
     */
    fun convert(notification: T): Any
}