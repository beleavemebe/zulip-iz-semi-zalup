package com.example.coursework.feature.channels.data.model.response

import com.example.coursework.feature.channels.data.model.dto.StreamDto

@kotlinx.serialization.Serializable
data class AllStreamsResponse(
    val streams: List<StreamDto>? = null
)
