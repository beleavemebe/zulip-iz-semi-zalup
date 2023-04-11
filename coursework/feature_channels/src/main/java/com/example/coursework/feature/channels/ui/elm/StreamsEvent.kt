package com.example.coursework.feature.channels.ui.elm

import com.example.coursework.feature.channels.ui.model.StreamUi
import com.example.coursework.feature.channels.ui.model.StreamsTab
import com.example.coursework.feature.channels.ui.model.TopicUi

sealed interface StreamsEvent {
    object Init : StreamsEvent

    sealed interface Ui : StreamsEvent {
        data class UpdateSearchQuery(val value: String) : Ui
        data class SelectStreamsTab(val tab: StreamsTab) : Ui
        data class ClickStream(val streamUi: StreamUi) : Ui
        data class ClickTopic(val topicUi: TopicUi) : Ui
    }

    sealed interface Internal : StreamsEvent {
        data class StreamsLoaded(val streamUis: List<StreamUi>) : Internal
        data class TopicsLoaded(val streamUi: StreamUi, val topicUis: List<TopicUi>) : Internal
        data class CaughtError(val error: Throwable) : Internal
    }
}
