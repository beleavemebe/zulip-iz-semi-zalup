package com.example.coursework.chat.data

import com.example.coursework.chat.data.MessageRepository.currentUserId
import com.example.coursework.chat.model.Message
import com.example.coursework.chat.model.Reaction
import com.example.coursework.chat.util.emojis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

object MessageRepository {
    const val currentUserId = "0"

    private val _messages = MutableStateFlow(fakeMessages)
    val messages: Flow<List<Message>> = _messages.asStateFlow()

    suspend fun sendMessage(
        text: String
    ) = withContext(Dispatchers.Default) {

    }

    suspend fun sendOrRevokeReaction(
        messageId: String,
        emoji: String
    ) = withContext(Dispatchers.Default) {

    }

    suspend fun loadMessages(
        stream: String,
        topic: String
    ): List<Message> {
        
    }
}
