package com.example.coursework.shared_messages.api.domain.model

data class MessagesResult(
    val containsOldestMessage: Boolean = false,
    val containsNewestMessage: Boolean = false,
    val messages: List<Message> = emptyList()
)
