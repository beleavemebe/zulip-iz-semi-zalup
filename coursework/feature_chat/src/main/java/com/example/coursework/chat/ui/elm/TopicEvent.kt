package com.example.coursework.chat.ui.elm

import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.chat.ui.model.TopicItem

sealed interface TopicEvent {
    sealed interface Ui : TopicEvent {
        data class Init(val stream: Int, val topic: String) : Ui
        data class ToggleReaction(val messageUi: MessageUi, val emoteName: String) : Ui
        data class UpdateInputText(val value: String) : Ui
        object ClickSendMessage : Ui
    }

    sealed interface Internal : TopicEvent {
        data class MessagesLoaded(val messages: List<TopicItem>) : Internal
        data class CaughtError(val error: Throwable) : Internal
    }
}
