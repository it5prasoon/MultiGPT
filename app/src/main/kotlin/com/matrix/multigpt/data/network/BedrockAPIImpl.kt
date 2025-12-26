package com.matrix.multigpt.data.network

import com.matrix.multigpt.data.dto.bedrock.BedrockCredentials
import com.matrix.multigpt.data.dto.bedrock.BedrockError
import com.matrix.multigpt.data.dto.bedrock.BedrockStreamChunk
import com.matrix.multigpt.data.dto.bedrock.ConverseContent
import com.matrix.multigpt.data.dto.bedrock.ConverseInferenceConfig
import com.matrix.multigpt.data.dto.bedrock.ConverseMessage
import com.matrix.multigpt.data.dto.bedrock.ConverseRequest
import com.matrix.multigpt.data.dto.bedrock.ConverseSystemMessage
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class BedrockAPIImpl @Inject constructor(
    private val networkClient: NetworkClient
) : BedrockAPI {

    private var bedrockCredentials: BedrockCredentials? = null

    override fun setBedrockCredentials(credentials: BedrockCredentials?) {
        this.bedrockCredentials = credentials
    }

    override fun streamChatMessage(
        messages: List<Pair<String, String>>,
        model: String,
        systemPrompt: String?,
        temperature: Double?,
        maxTokens: Int?,
        topP: Double?
    ): Flow<BedrockStreamChunk> {
        
        return flow {
            try {
                val creds = bedrockCredentials ?: run {
                    emit(BedrockStreamChunk(error = BedrockError(type = "auth_error", message = "Bedrock credentials not configured")))
                    return@flow
                }

                // Use Bearer token authentication with Converse API
                if (creds.apiKey.isBlank()) {
                    emit(BedrockStreamChunk(error = BedrockError(type = "auth_error", message = "Bedrock API key is required")))
                    return@flow
                }
                
                val converseUri = "/model/${model}/converse"
                val converseEndpoint = "https://bedrock-runtime.us-east-1.amazonaws.com${converseUri}"
                val converseRequest = createConverseRequest(messages, systemPrompt, temperature, maxTokens, topP)
                val converseBody = Json.encodeToString(ConverseRequest.serializer(), converseRequest)
                
                val builder = HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url(converseEndpoint)
                    contentType(ContentType.Application.Json)
                    setBody(converseBody)
                    headers {
                        append("Authorization", "Bearer ${creds.apiKey}")
                        append("Content-Type", "application/json")
                    }
                }

                HttpStatement(builder = builder, client = networkClient()).execute { response ->
                    streamConverseEventsFrom(response)
                }
                
            } catch (e: Exception) {
                emit(BedrockStreamChunk(error = BedrockError(type = "network_error", message = e.message ?: "Unknown error")))
            }
        }
    }


    private fun createConverseRequest(
        messages: List<Pair<String, String>>,
        systemPrompt: String?,
        temperature: Double?,
        maxTokens: Int?,
        topP: Double?
    ): ConverseRequest {
        val converseMessages = messages.map { (role, content) ->
            ConverseMessage(
                role = role,
                content = listOf(ConverseContent(text = content))
            )
        }
        
        val systemMessages = if (!systemPrompt.isNullOrBlank()) {
            listOf(ConverseSystemMessage(text = systemPrompt))
        } else null
        
        val inferenceConfig = if (maxTokens != null || temperature != null || topP != null) {
            ConverseInferenceConfig(
                maxTokens = maxTokens,
                temperature = temperature,
                topP = topP
            )
        } else null
        
        return ConverseRequest(
            messages = converseMessages,
            system = systemMessages,
            inferenceConfig = inferenceConfig
        )
    }

    private suspend fun FlowCollector<BedrockStreamChunk>.streamConverseEventsFrom(
        response: HttpResponse
    ) {
        val responseBody = response.body<String>()
        val jsonInstance = Json { ignoreUnknownKeys = true }
        
        try {
            // Parse the Converse API response
            val converseResponse = Json.parseToJsonElement(responseBody).jsonObject
            
            if (converseResponse.containsKey("output")) {
                val output = converseResponse["output"]?.jsonObject
                val message = output?.get("message")?.jsonObject
                val contentArray = message?.get("content")?.jsonArray
                val content = contentArray?.firstOrNull()?.jsonObject
                val text = content?.get("text")?.jsonPrimitive?.content
                
                if (!text.isNullOrBlank()) {
                    emit(BedrockStreamChunk(content_block = com.matrix.multigpt.data.dto.bedrock.BedrockContentBlock(type = "text", text = text)))
                }
            }
            
            if (converseResponse.containsKey("error")) {
                val error = converseResponse["error"]?.jsonObject
                val errorMessage = error?.get("message")?.jsonPrimitive?.content ?: "Unknown error"
                emit(BedrockStreamChunk(error = BedrockError(type = "api_error", message = errorMessage)))
            }
            
        } catch (e: Exception) {
            emit(BedrockStreamChunk(error = BedrockError(type = "parse_error", message = "Failed to parse response: ${e.message}")))
        }
    }

}
