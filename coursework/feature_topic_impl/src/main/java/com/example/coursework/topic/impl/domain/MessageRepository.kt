package com.example.coursework.topic.impl.domain

import com.example.coursework.topic.impl.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun loadNewestMessages(
        stream: Int,
        topic: String,
        forceRemote: Boolean = false
    ): Flow<List<Message>>

    suspend fun loadPreviousPage(
        stream: Int,
        topic: String,
        anchor: Int,
    ): List<Message>

    suspend fun loadNextPage(
        stream: Int,
        topic: String,
        anchor: Int,
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
