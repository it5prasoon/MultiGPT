package com.matrix.multigpt.data.dto.bedrock

import kotlinx.serialization.Serializable

@Serializable
enum class BedrockAuthMethod {
    API_KEY        // Bearer token authentication
}

@Serializable
data class BedrockCredentials(
    val authMethod: BedrockAuthMethod = BedrockAuthMethod.API_KEY,
    // For API Key authentication
    val apiKey: String = ""
)
