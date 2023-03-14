package com.example.coursework.chat.model

import java.time.LocalDateTime

data class Message(
    val id: String,
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val posted: LocalDateTime,
    val reactions: List<Reaction>,
)
