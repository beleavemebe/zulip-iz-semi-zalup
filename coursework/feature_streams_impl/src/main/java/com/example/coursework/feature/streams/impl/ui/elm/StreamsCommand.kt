package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab

sealed interface StreamsCommand {
    data class LoadStreams(
        val tab: StreamsTab,
        val query: String,
        val forceRemote: Boolean = false
    ) : StreamsCommand

    data class LoadTopics(val streamUi: StreamUi) : StreamsCommand
}
