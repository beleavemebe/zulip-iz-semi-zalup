package com.example.coursework.topic.impl.data

import com.example.coursework.topic.impl.data.api.MessagesApi
import com.example.coursework.topic.impl.data.model.Narrow
import com.example.coursework.topic.impl.domain.MessageRepository
import com.example.coursework.topic.impl.domain.model.Message
import com.google.gson.Gson
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
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
}
