package com.donghwan.easywebhook.webhook.client

import com.donghwan.easywebhook.webhook.WebhookMessage
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverter
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverterManager
import com.donghwan.easywebhook.webhook.converter.WebhookMessageConverterManagerImpl
import com.donghwan.easywebhook.webhook.model.WebhookNotification
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class OkHttpWebhookClient(
    private val converterManager: WebhookMessageConverterManager = WebhookMessageConverterManagerImpl(),
    private val client: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson()
) : WebhookClient {

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    override fun sendMessage(message: WebhookMessage) {
        val converter = converterManager.findConverter(message.type, message.body)

        if (!converter.supports(message.type, message.body)) {
            throw IllegalArgumentException("No suitable converter found for ${message.type}")
        }

        @Suppress("UNCHECKED_CAST")
        val typedConverter = converter as WebhookMessageConverter<WebhookNotification>

        val converted = typedConverter.convert(notification = message.body)
        val json = gson.toJson(converted)
        val body = json.toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url(message.url)
            .post(body)
            .build()
        println(json)

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw WebhookClientException("Webhook request failed: ${response.code} ${response.body?.string()}")
            }
        }
    }
}