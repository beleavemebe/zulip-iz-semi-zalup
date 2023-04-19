package com.example.coursework.feature.streams.impl.data.model.response

import com.example.coursework.feature.streams.impl.data.model.dto.StreamDto

@kotlinx.serialization.Serializable
data class AllStreamsResponse(
    val streams: List<StreamDto>? = null
)
