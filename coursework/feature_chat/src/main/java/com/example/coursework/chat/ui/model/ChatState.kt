package com.example.coursework.chat.ui.model

data class ChatState(
    val items: List<ChatItem> = emptyList(),
    val inputText: String = "",
    val isSendButtonVisible: Boolean = false,
)
