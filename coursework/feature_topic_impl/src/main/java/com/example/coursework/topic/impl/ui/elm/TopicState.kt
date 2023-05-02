package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.ui.model.TopicItem

data class TopicState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val items: List<TopicItem> = emptyList(),
    val inputText: String = "",
    val isSendButtonVisible: Boolean = false,
    val isLoadingPage: Boolean = false,
    val oldestAnchor: Int = 0,
    val canLoadOlderMessages: Boolean = true,
    val newestAnchor: Int = 0,
    val canLoadNewerMessages: Boolean = true,
)
