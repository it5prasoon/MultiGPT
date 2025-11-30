package com.matrix.multigpt.data.dto.anthropic.request

import com.matrix.multigpt.data.dto.anthropic.common.MessageContent
import com.matrix.multigpt.data.dto.anthropic.common.MessageRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InputMessage(
    @SerialName("role")
    val role: MessageRole,

    @SerialName("content")
    val content: List<MessageContent>
)
