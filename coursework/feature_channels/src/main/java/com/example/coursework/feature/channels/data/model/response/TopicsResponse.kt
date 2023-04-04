package com.example.coursework.feature.channels.data.model.response

import com.example.coursework.feature.channels.data.model.dto.TopicDto

@kotlinx.serialization.Serializable
data class TopicsResponse(
    val topics: List<TopicDto>? = null
)
