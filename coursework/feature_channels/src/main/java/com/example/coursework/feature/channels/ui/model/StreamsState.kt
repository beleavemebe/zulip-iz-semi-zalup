package com.example.coursework.feature.channels.ui.model

data class StreamsState(
    val isLoading: Boolean = true,
    val streamsTab: StreamsTab = StreamsTab.SUBSCRIBED,
    val items: List<StreamsItem> = emptyList(),
    val error: Throwable? = null
)
