package com.example.coursework.feature.streams.impl.data.model.response

import com.example.coursework.feature.streams.impl.data.model.dto.SubscribedStreamDto

@kotlinx.serialization.Serializable
data class SubscribedStreamsResponse(
    val subscriptions: List<SubscribedStreamDto>? = null
)
