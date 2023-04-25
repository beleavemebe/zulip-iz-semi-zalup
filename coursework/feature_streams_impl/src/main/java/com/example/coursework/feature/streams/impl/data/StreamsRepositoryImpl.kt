package com.example.coursework.feature.streams.impl.data

import com.example.coursework.feature.streams.impl.data.api.StreamsApi
import com.example.coursework.feature.streams.impl.data.db.StreamEntity
import com.example.coursework.feature.streams.impl.data.db.StreamsDao
import com.example.coursework.feature.streams.impl.data.db.TopicEntity
import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic
import com.example.coursework.feature.streams.impl.domain.repository.StreamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@StreamsScope
class StreamsRepositoryImpl @Inject constructor(
    private val api: StreamsApi,
    private val dao: StreamsDao,
) : StreamsRepository {
    private val mapper = StreamsMapper

    override suspend fun getAllStreams(): Flow<List<Stream>> = getStreams(subscribed = false)

    override suspend fun getSubscribedStreams() = getStreams(subscribed = true)

    private fun getStreams(subscribed: Boolean) = flow {
        val localStreams = requireNotNull(dao.getStreams(subscribed = subscribed)).map(mapper::toStream)
        emit(localStreams)
        val remoteStreams = requireNotNull(api.getSubscribedStreams().subscriptions).map(mapper::toStream)
        emit(remoteStreams)
        writeStreamsToDb(remoteStreams, subscribed)
    }

    private suspend fun writeStreamsToDb(
        remoteStreams: List<Stream>,
        subscribed: Boolean,
    ) {
        dao.clearStreams(subscribed)
        val entities = remoteStreams.map {
            StreamEntity(
                id = it.id,
                name = it.name,
                subscribed = subscribed
            )
        }
        dao.writeStreams(entities)
    }

    override suspend fun getTopics(streamId: Int): Flow<List<Topic>> = flow {
        val localTopics = requireNotNull(dao.getTopicsForStream(streamId)).map(mapper::toTopic)
        emit(localTopics)
        val remoteTopics = requireNotNull(api.getStreamTopics(streamId).topics).map(mapper::toTopic)
        emit(remoteTopics)
        dao.clearTopics()
        dao.writeTopics(
            remoteTopics.map {
                TopicEntity(
                    it.name,
                    streamId
                )
            }
        )
    }
}
