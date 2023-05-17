package com.example.shared_streams.impl.coursework.data.model.response

import com.example.shared_streams.impl.coursework.data.model.dto.TopicDto

@kotlinx.serialization.Serializable
data class TopicsResponse(
    val topics: List<TopicDto>? = null
)
