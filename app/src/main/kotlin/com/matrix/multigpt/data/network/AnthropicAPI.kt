package com.matrix.multigpt.data.network

import com.matrix.multigpt.data.dto.anthropic.request.MessageRequest
import com.matrix.multigpt.data.dto.anthropic.response.MessageResponseChunk
import kotlinx.coroutines.flow.Flow

interface AnthropicAPI {
    fun setToken(token: String?)
    fun setAPIUrl(url: String)
    fun streamChatMessage(messageRequest: MessageRequest): Flow<MessageResponseChunk>
}
