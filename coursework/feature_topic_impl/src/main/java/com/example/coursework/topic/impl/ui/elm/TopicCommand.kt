package com.example.coursework.topic.impl.ui.elm

sealed interface TopicCommand {
    data class LoadNewestMessages(
        val stream: Int,
        val topic: String,
    ) : TopicCommand

    data class LoadPreviousPage(
        val stream: Int,
        val topic: String,
        val anchorMessageId: Int
    ) : TopicCommand

    data class LoadNextPage(
        val stream: Int,
        val topic: String,
        val anchorMessageId: Int
    ) : TopicCommand
    
    data class SendReaction(
        val stream: Int,
        val topic: String,
        val id: Int,
        val emoteName: String,
    ) : TopicCommand
    
    data class RevokeReaction(
        val stream: Int,
        val topic: String,
        val id: Int,
        val emoteName: String,
    ) : TopicCommand
    
    data class SendMessage(
        val stream: Int,
        val topic: String,
        val message: String
    ) : TopicCommand

    data class DeleteMessage(val messageId: Int) : TopicCommand

    data class EditMessage(
        val messageId: Int,
        val oldContent: String,
        val updatedContent: String,
    ) : TopicCommand
}
