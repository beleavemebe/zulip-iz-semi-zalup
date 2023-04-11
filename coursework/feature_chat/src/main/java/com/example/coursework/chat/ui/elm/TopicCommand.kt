package com.example.coursework.chat.ui.elm

sealed interface TopicCommand {
    data class LoadAllMessages(
        val stream: Int,
        val topic: String,
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
}
