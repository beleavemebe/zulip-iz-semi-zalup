package com.example.coursework.feature.streams.impl.data.model.response

import com.example.coursework.feature.streams.impl.data.model.dto.TopicDto

@kotlinx.serialization.Serializable
data class TopicsResponse(
    val topics: List<TopicDto>? = null
)
