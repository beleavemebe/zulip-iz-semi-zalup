package com.example.coursework.feature.channels.ui.elm

import com.example.coursework.feature.channels.ui.model.StreamUi
import com.example.coursework.feature.channels.ui.model.StreamsTab

sealed interface StreamsCommand {
    data class LoadStreams(val tab: StreamsTab, val query: String) : StreamsCommand
    data class LoadTopics(val streamUi: StreamUi) : StreamsCommand
}
