package com.example.coursework.topic.impl.domain.model

data class MessagesResult(
    val containsOldestMessage: Boolean = false,
    val containsNewestMessage: Boolean = false,
    val messages: List<Message> = emptyList()
)
