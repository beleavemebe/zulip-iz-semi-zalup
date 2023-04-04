package com.example.coursework.feature.channels.data.model.response

import com.example.coursework.feature.channels.data.model.dto.SubscribedStreamDto

@kotlinx.serialization.Serializable
data class SubscribedStreamsResponse(
    val subscriptions: List<SubscribedStreamDto>? = null
)
