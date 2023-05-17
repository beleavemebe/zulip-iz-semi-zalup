package com.example.coursework.feature.create_topic.impl.ui.elm

sealed interface CreateTopicCommand {
    data class LoadOccupiedTopicNames(
        val streamId: Int
    ) : CreateTopicCommand

    data class CreateTopic(
        val occupiedTopicNames: List<String>?,
        val streamId: Int,
        val topicName: String,
        val firstMessage: String
    ) : CreateTopicCommand
}
