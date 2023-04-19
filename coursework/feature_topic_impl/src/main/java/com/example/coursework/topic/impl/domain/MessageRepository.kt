package com.example.coursework.topic.impl.domain

import com.example.coursework.topic.impl.domain.model.Message

interface MessageRepository {
    suspend fun loadMessages(
        stream: Int,
        topic: String,
    ): List<Message>

    suspend fun sendReaction(
        messageId: Int,
        name: String,
    )

    suspend fun revokeReaction(
        messageId: Int,
        name: String,
    )

    suspend fun sendMessage(
        stream: Int,
        topic: String,
        text: String,
    )
}
