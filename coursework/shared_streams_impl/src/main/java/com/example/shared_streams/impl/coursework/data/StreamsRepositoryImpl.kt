package com.example.shared_streams.impl.coursework.data

import com.example.coursework.core.utils.CacheContainer
import com.example.coursework.core.utils.cache
import com.example.coursework.shared_streams.api.domain.model.Stream
import com.example.coursework.shared_streams.api.domain.model.Topic
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.example.shared_streams.impl.coursework.data.api.StreamsApi
import com.example.shared_streams.impl.coursework.data.db.StreamsDao
import com.example.shared_streams.impl.coursework.data.model.dto.CreateStreamDto
import com.example.shared_streams.impl.coursework.di.SharedStreamsScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@SharedStreamsScope
class StreamsRepositoryImpl @Inject constructor(
    private val api: StreamsApi,
    private val dao: StreamsDao,
) : StreamsRepository, CacheContainer by CacheContainer.Map() {
    private val mapper = StreamsMapper

    override fun getAllStreams(forceRemote: Boolean): Flow<List<Stream>> = getStreams(subscribed = false, forceRemote)

    override fun getSubscribedStreams(forceRemote: Boolean) = getStreams(subscribed = true, forceRemote)

    private fun getStreams(subscribed: Boolean, forceRemote: Boolean) = flow {
        val localStreams = dao.getStreams(subscribed).map(mapper::toStream)
        if (localStreams.isNotEmpty()) {
            emit(localStreams)
        }

        val remoteStreams = getRemoteStreams(subscribed, forceRemote)
        emit(remoteStreams)
        writeStreamsToDb(remoteStreams, subscribed)
    }

    private suspend fun getRemoteStreams(subscribed: Boolean, forceRemote: Boolean): List<Stream> =
        cache("getRemoteStreams_$subscribed", forceUpdate = forceRemote) {
            if (subscribed) {
                requireNotNull(api.getSubscribedStreams().subscriptions)
            } else {
                requireNotNull(api.getAllStreams().streams)
            }.map(mapper::toStream)
        }

    private suspend fun writeStreamsToDb(
        remoteStreams: List<Stream>,
        subscribed: Boolean,
    ) {
        dao.clearStreams(subscribed)
        val entities = remoteStreams.map { stream ->
            mapper.toEntity(stream, subscribed)
        }
        dao.writeStreams(entities)
    }

    override fun getTopics(streamId: Int): Flow<List<Topic>> = flow {
        val localTopics = requireNotNull(dao.getTopicsForStream(streamId)).map(mapper::toTopic)
        if (localTopics.isNotEmpty()) {
            emit(localTopics)
        }

        val remoteTopics = requireNotNull(api.getStreamTopics(streamId).topics).map(mapper::toTopic)
        emit(remoteTopics)
        dao.clearTopics()
        val entities = remoteTopics.map { topic ->
            mapper.toEntity(topic, streamId)
        }
        dao.writeTopics(entities)
    }

    override suspend fun createStream(name: String) {
        val stream = listOf(CreateStreamDto(name))
        val json = Json.encodeToString(stream)
        api.createStream(json)
    }
}
