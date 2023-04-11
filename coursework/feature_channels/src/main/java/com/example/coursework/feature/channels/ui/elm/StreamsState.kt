package com.example.coursework.feature.channels.ui.elm

import com.example.coursework.feature.channels.ui.model.StreamsItem
import com.example.coursework.feature.channels.ui.model.StreamsTab

data class StreamsState(
    val isLoading: Boolean = true,
    val query: String = "",
    val streamsTab: StreamsTab = StreamsTab.SUBSCRIBED,
    val items: List<StreamsItem> = emptyList(),
    val error: Throwable? = null
)
