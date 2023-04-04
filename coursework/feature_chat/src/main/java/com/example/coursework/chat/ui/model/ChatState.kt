package com.example.coursework.chat.ui.model

data class ChatState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val items: List<ChatItem> = emptyList(),
    val inputText: String = "",
    val isSendButtonVisible: Boolean = false,
)
