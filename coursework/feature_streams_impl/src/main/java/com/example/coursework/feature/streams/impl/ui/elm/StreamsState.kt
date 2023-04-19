package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.ui.model.StreamsItem
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab

data class StreamsState(
    val isLoading: Boolean = true,
    val query: String = "",
    val streamsTab: StreamsTab = StreamsTab.SUBSCRIBED,
    val items: List<StreamsItem> = emptyList(),
    val error: Throwable? = null
)
