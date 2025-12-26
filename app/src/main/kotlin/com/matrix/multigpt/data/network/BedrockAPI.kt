package com.matrix.multigpt.data.network

import com.matrix.multigpt.data.dto.bedrock.BedrockCredentials
import com.matrix.multigpt.data.dto.bedrock.BedrockStreamChunk
import kotlinx.coroutines.flow.Flow

interface BedrockAPI {
    fun setBedrockCredentials(credentials: BedrockCredentials?)
    
    fun streamChatMessage(
        messages: List<Pair<String, String>>,
        model: String,
        systemPrompt: String? = null,
        temperature: Double? = null,
        maxTokens: Int? = null,
        topP: Double? = null
    ): Flow<BedrockStreamChunk>
}
