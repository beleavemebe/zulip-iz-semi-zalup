package com.example.coursework.topic.impl.data.model.response

import com.example.coursework.topic.impl.data.model.dto.MessageDto

@kotlinx.serialization.Serializable
data class MessagesResponse(
    val anchor: Long,
    val found_anchor: Boolean,
    val found_newest: Boolean,
    val found_oldest: Boolean,
    val history_limited: Boolean,
    val messages: List<MessageDto>,
    val msg: String,
    val result: String
)
