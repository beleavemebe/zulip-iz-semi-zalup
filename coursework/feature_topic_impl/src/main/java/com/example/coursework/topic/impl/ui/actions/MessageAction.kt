package com.example.coursework.topic.impl.ui.actions

sealed interface MessageAction {
    data class PickReaction(
        val messageId: Int,
        val emoteName: String,
    ) : MessageAction

    data class DeleteMessage(
        val messageId: Int,
    ) : MessageAction

    data class EditMessage(
        val messageId: Int,
        val oldContent: String,
        val updatedContent: String,
    ) : MessageAction

    data class CopyMessage(
        val message: String,
    ) : MessageAction
}
