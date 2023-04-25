package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.ui.model.MessageUi

sealed interface TopicEvent {
    sealed interface Ui : TopicEvent {
        data class Init(val stream: Int, val topic: String) : Ui
        data class ToggleReaction(val messageUi: MessageUi, val emoteName: String) : Ui
        data class UpdateInputText(val value: String) : Ui
        object ClickSendMessage : Ui
        object LoadPreviousPage : TopicEvent
        object LoadNextPage : TopicEvent
    }

    sealed interface Internal : TopicEvent {
        data class MessagesLoaded(val messages: List<MessageUi>) : Internal
        data class PreviousPageLoaded(val messages: List<MessageUi>) : Internal
        data class NextPageLoaded(val messages: List<MessageUi>) : Internal
        data class CaughtError(val error: Throwable) : Internal
    }
}
