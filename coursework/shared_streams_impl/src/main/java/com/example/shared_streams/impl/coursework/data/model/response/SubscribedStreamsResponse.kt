package com.example.shared_streams.impl.coursework.data.model.response

import com.example.shared_streams.impl.coursework.data.model.dto.StreamDto

@kotlinx.serialization.Serializable
data class SubscribedStreamsResponse(
    val subscriptions: List<StreamDto>? = null
)
