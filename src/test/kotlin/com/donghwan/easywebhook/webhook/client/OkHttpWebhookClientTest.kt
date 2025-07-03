import com.donghwan.easywebhook.webhook.WebhookMessage
import com.donghwan.easywebhook.webhook.WebhookType
import com.donghwan.easywebhook.webhook.client.OkHttpWebhookClient
import com.donghwan.easywebhook.webhook.client.WebhookClientException
import com.donghwan.easywebhook.webhook.model.impls.SimpleWebhookNotification
import com.google.gson.Gson
import okhttp3.*
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class OkHttpWebhookClientTest {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var call: Call
    private lateinit var client: OkHttpWebhookClient
    private val gson = Gson()

    @BeforeEach
    fun setup() {
        okHttpClient = mock()
        call = mock()
        client = OkHttpWebhookClient(okHttpClient, gson)
    }

    @Test
    fun send_SuccessfulResponse_DoesNotThrow() {
        val webhookUrl = "http://dummy.url/webhook"
        val notification = SimpleWebhookNotification("Test Title", "Test Content")
        val message = WebhookMessage(WebhookType.SLACK, webhookUrl, notification)

        val response = Response.Builder()
            .request(Request.Builder().url(webhookUrl).build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(null, ""))
            .build()

        `when`(okHttpClient.newCall(any())).thenReturn(call)
        `when`(call.execute()).thenReturn(response)

        assertDoesNotThrow {
            client.sendMessage(message)
        }

        verify(okHttpClient).newCall(any())
        verify(call).execute()
    }

    @Test
    fun send_FailedResponse_ThrowsException() {
        val webhookUrl = "http://dummy.url/webhook"
        val notification = SimpleWebhookNotification("Test Title", "Test Content")
        val message = WebhookMessage(WebhookType.SLACK, webhookUrl, notification)

        val response = Response.Builder()
            .request(Request.Builder().url(webhookUrl).build())
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message("Internal Server Error")
            .body(ResponseBody.create(null, ""))
            .build()

        `when`(okHttpClient.newCall(any())).thenReturn(call)
        `when`(call.execute()).thenReturn(response)

        val exception = assertThrows<WebhookClientException> {
            client.sendMessage(message)
        }

        assertTrue(exception.message!!.contains("Webhook request failed"))
        verify(okHttpClient).newCall(any())
        verify(call).execute()
    }
}