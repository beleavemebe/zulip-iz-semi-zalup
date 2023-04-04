package com.example.coursework.chat.ui.model

data class TopicState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val items: List<TopicItem> = emptyList(),
    val inputText: String = "",
    val isSendButtonVisible: Boolean = false,
)
