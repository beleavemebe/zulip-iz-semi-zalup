package com.example.coursework.feature.create_topic.impl.ui.elm

import com.example.coursework.feature.create_topic.impl.domain.CreateTopicResult

data class CreateTopicState(
    val streamId: Int = 0,
    val stream: String = "",
    val topicName: String = "",
    val occupiedTopicNames: List<String>? = null,
    val firstMessage: String = "",
    val createTopicResult: CreateTopicResult? = null
)
