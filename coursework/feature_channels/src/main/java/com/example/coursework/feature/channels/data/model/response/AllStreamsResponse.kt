package com.example.coursework.feature.channels.data.model

@kotlinx.serialization.Serializable
data class AllStreamsResponse(
    val streams: List<StreamDto>? = null
)
