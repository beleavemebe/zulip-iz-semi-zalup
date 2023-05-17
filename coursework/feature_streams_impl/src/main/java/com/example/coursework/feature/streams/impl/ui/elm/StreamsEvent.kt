package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab
import com.example.coursework.feature.streams.impl.ui.model.TopicUi

sealed interface StreamsEvent {
    object Init : StreamsEvent

    sealed interface Ui : StreamsEvent {
        object ClickCreateStream : StreamsEvent
        object ClickViewAllMessages : StreamsEvent
        data class ClickCreateTopic(val streamUi: StreamUi) : StreamsEvent
        data class UpdateSearchQuery(val value: String) : Ui
        data class SelectStreamsTab(val tab: StreamsTab) : Ui
        data class ClickStream(val streamUi: StreamUi) : Ui
        data class ClickTopic(val topicUi: TopicUi) : Ui
    }

    sealed interface Internal : StreamsEvent {
        data class StreamsLoaded(val streamUis: List<StreamUi>) : Internal
        data class TopicsLoaded(val streamUi: StreamUi, val topicUis: List<TopicUi>) : Internal
        data class CaughtError(val error: Throwable) : Internal
        data class StreamCreated(val name: String) : Internal
    }
}
