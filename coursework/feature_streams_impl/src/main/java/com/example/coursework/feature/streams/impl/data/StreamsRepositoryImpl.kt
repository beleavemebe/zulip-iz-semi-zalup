package com.example.coursework.feature.streams.impl.data

import com.example.coursework.feature.streams.impl.data.api.StreamsApi
import com.example.coursework.feature.streams.impl.data.db.StreamsDao
import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic
import com.example.coursework.feature.streams.impl.domain.repository.StreamsRepository
import javax.inject.Inject

@StreamsScope
class StreamsRepositoryImpl @Inject constructor(
    private val api: StreamsApi,
    private val dao: StreamsDao
) : StreamsRepository {
    private val mapper = StreamsMapper

    override suspend fun getAllStreams(): List<Stream> {
        return requireNotNull(api.getAllStreams().streams)
            .map(mapper::toStream)
    }

    override suspend fun getSubscribedStreams(): List<Stream> {
        return requireNotNull(api.getSubscribedStreams().subscriptions)
            .map(mapper::toStream)
    }

    override suspend fun getTopics(streamId: Int): List<Topic> {
        return requireNotNull(api.getStreamTopics(streamId).topics)
            .map(mapper::toTopic)
    }
}
