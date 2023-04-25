package com.example.coursework.topic.impl.domain.model

import java.time.LocalDateTime

data class Message(
    val id: Int,
    val topic: String,
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val posted: LocalDateTime,
    val reactions: List<Reaction>,
)
