package com.example.coursework.feature.create_topic.impl.ui.elm

import com.example.coursework.feature.create_topic.impl.domain.CreateTopicResult

sealed interface CreateTopicEvent {
    sealed interface Ui : CreateTopicEvent {
        data class Init(val streamId: Int, val stream: String) : Ui
        object ClickGoBack : CreateTopicEvent
        data class UpdateTopicTitle(val value: String) : CreateTopicEvent
        data class UpdateFirstMessage(val value: String) : CreateTopicEvent
        object ClickCreateTopic : CreateTopicEvent
    }

    sealed interface Internal : CreateTopicEvent {
        data class OccupiedTopicNamesLoaded(val values: List<String>) : Internal
        data class TopicCreateResultObtained(val createTopicResult: CreateTopicResult) : Internal
    }
}
