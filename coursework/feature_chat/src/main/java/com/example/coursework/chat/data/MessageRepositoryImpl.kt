package com.example.coursework.chat.data

import com.example.coursework.chat.data.api.MessagesApi
import com.example.coursework.chat.data.model.Narrow
import com.example.coursework.chat.domain.MessageRepository
import com.example.coursework.chat.domain.model.Message
import com.example.coursework.core.network.NetworkModule
import com.google.gson.Gson

class MessageRepositoryImpl(
    private val api: MessagesApi,
) : MessageRepository {
    override suspend fun loadMessages(
        stream: Int,
        topic: String
    ): List<Message> {
        val narrow = Narrow("stream" to stream, "topic" to topic)
        return api.getMessages(
            anchor = "newest",
            numAfter = 0,
            numBefore = 1000,
            narrow = Gson().toJson(narrow) // TODO разобраться с generic contextual serializers в kotlinx.serialization
        ).messages.map(MessagesMapper::toMessage)
    }

    override suspend fun sendReaction(
        messageId: Int,
        name: String
    ) {
        api.addReaction(messageId, name)
    }

    override suspend fun revokeReaction(
        messageId: Int,
        name: String
    ) {
        api.deleteReaction(messageId, name)
    }

    override suspend fun sendMessage(
        stream: Int,
        topic: String,
        text: String
    ) {
        api.sendMessage(
            stream = stream,
            topic = topic,
            content = text
        )
    }

    companion object {
        val instance by lazy {
            MessageRepositoryImpl(
                api = MessagesApi.create(NetworkModule.retrofit)
            )
        }
    }
}
