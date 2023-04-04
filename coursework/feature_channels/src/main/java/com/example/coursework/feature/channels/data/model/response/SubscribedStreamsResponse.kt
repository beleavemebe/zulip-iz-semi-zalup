package com.example.coursework.feature.channels.data.model

@kotlinx.serialization.Serializable
data class SubscribedStreamsResponse(
    val subscriptions: List<SubscribedStreamDto>? = null
)
