package com.example.coursework.shared_messages.api.domain

import com.example.coursework.shared_messages.api.domain.model.MessagesResult
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun loadNewestMessages(
        stream: Int,
        topic: String,
        forceRemote: Boolean = false
    ): Flow<MessagesResult>

    suspend fun loadPreviousPage(
        stream: Int,
        topic: String,
        anchor: Int,
    ): MessagesResult

    suspend fun loadNextPage(
        stream: Int,
        topic: String,
        anchor: Int,
    ): MessagesResult

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

    suspend fun deleteMessage(id: Int)

    suspend fun editMessage(id: Int, content: String)
}
