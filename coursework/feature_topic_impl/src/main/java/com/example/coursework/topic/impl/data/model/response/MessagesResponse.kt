package com.example.coursework.topic.impl.data.model.response

import com.example.coursework.topic.impl.data.model.dto.MessageDto

@kotlinx.serialization.Serializable
data class MessagesResponse(
    val anchor: Long? = null,
    val found_anchor: Boolean? = null,
    val found_newest: Boolean,
    val found_oldest: Boolean,
    val history_limited: Boolean? = null,
    val msg: String? = null,
    val result: String? = null,
    val messages: List<MessageDto>,
)
