package com.example.coursework.feature.channels.ui.model

data class ChannelsState(
    val isLoading: Boolean = true,
    val streamsTab: StreamsTab = StreamsTab.SUBSCRIBED,
    val items: List<ChannelsItem> = emptyList(),
    val error: Throwable? = null
)
