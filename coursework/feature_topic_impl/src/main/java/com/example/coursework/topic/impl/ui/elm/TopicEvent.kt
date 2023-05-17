package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.ui.model.MessageUi

sealed interface TopicEvent {
    sealed interface Ui : TopicEvent {
        data class Init(val stream: Int, val topic: String) : Ui

        data class ToggleReaction(val messageId: Int, val emoteName: String) : Ui

        data class UpdateInputText(val value: String) : Ui

        data class DeleteMessage(val messageId: Int) : Ui

        data class EditMessage(
            val messageId: Int,
            val oldContent: String,
            val updatedContent: String,
        ) : Ui

        data class CopyMessage(val message: String) : Ui

        object ClickGoBack : Ui

        object ClickSendMessage : Ui

        object LoadPreviousPage : Ui

        object LoadNextPage : Ui
    }

    sealed interface Internal : TopicEvent {
        data class MessagesLoaded(
            val hasReachedOldestMessage: Boolean,
            val hasReachedNewestMessage: Boolean,
            val messages: List<MessageUi>
        ) : Internal

        data class PreviousPageLoaded(
            val hasReachedTheEnd: Boolean,
            val messages: List<MessageUi>
        ) : Internal

        data class NextPageLoaded(
            val hasReachedTheEnd: Boolean,
            val messages: List<MessageUi>
        ) : Internal

        data class MessageEdited(
            val messageId: Int,
            val updatedContent: String
        ) : Internal

        data class CaughtError(val error: Throwable) : Internal
    }
}
