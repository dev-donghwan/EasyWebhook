package com.donghwan.easywebhook.webhook.client

import com.donghwan.easywebhook.webhook.WebhookMessage
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class OkHttpWebhookClient(
    private val client: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson()
) : WebhookClient {

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    override fun sendMessage(message: WebhookMessage) {
        val json = gson.toJson(message.body)
        val body = json.toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url(message.url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw WebhookClientException("Webhook request failed: ${response.code} ${response.message}")
            }
        }
    }
}