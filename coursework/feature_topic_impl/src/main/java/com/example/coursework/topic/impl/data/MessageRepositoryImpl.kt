package com.example.coursework.topic.impl.data

import com.example.coursework.topic.impl.data.api.MessagesApi
import com.example.coursework.topic.impl.data.db.TopicDao
import com.example.coursework.topic.impl.data.model.Narrow
import com.example.coursework.topic.impl.domain.MessageRepository
import com.example.coursework.topic.impl.domain.model.Message
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val api: MessagesApi,
    private val dao: TopicDao,
) : MessageRepository {
    private val gson = Gson()

    override fun loadNewestMessages(
        stream: Int,
        topic: String,
        forceRemote: Boolean
    ): Flow<List<Message>> = flow {
        if (forceRemote.not()) {
            val localMessages = dao.getCachedMessages(topic).map(MessagesMapper::toMessage)
            if (localMessages.isNotEmpty()) {
                emit(localMessages)
            }
        }

        val remoteMessages = loadNewestMessages(stream, topic)
        emit(remoteMessages)
        writeMessagesToDb(topic, remoteMessages)
    }

    private suspend fun writeMessagesToDb(topic: String, messages: List<Message>) {
        dao.clearCacheForTopic(topic)
        dao.writeMessages(
            messages = messages.map { message ->
                MessagesMapper.toEntity(topic, message)
            }
        )
        dao.writeReactions(
            reactions = messages.flatMap { message ->
                message.reactions.map { reaction ->
                    MessagesMapper.toEntity(message.id, reaction)
                }
            }
        )
    }

    private suspend fun loadNewestMessages(
        stream: Int,
        topic: String,
    ) = api.getMessages(
        anchor = "newest",
        numBefore = PRELOADED_AMOUNT,
        numAfter = 0,
        narrow = getNarrowJson(stream, topic)
    ).messages.map { dto ->
        MessagesMapper.toMessage(topic, dto)
    }

    override suspend fun loadPreviousPage(
        stream: Int,
        topic: String,
        anchor: Int,
    ): List<Message> {
        return api.getMessages(
            anchor = anchor,
            numBefore = PAGE_SIZE,
            numAfter = 0,
            narrow = getNarrowJson(stream, topic)
        ).messages.map { dto ->
            MessagesMapper.toMessage(topic, dto)
        }
    }

    override suspend fun loadNextPage(
        stream: Int,
        topic: String,
        anchor: Int,
    ): List<Message> {
        return api.getMessages(
            anchor = anchor,
            numBefore = 0,
            numAfter = PAGE_SIZE,
            narrow = getNarrowJson(stream, topic)
        ).messages.map {
            dto -> MessagesMapper.toMessage(topic, dto)
        }
    }

    private fun getNarrowJson(
        stream: Int, topic: String
    ): String {
        // TODO разобраться с generic contextual serializers в kotlinx.serialization
        return gson.toJson(Narrow("stream" to stream, "topic" to topic))
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
        private const val PRELOADED_AMOUNT = 50
        private const val PAGE_SIZE = 20
    }
}
