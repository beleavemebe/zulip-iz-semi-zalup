package com.example.coursework.feature.channels.data

import com.example.coursework.core.network.NetworkModule
import com.example.coursework.feature.channels.data.api.StreamsApi
import com.example.coursework.feature.channels.domain.Stream
import com.example.coursework.feature.channels.domain.StreamsRepository
import com.example.coursework.feature.channels.domain.Topic

class StreamsRepositoryImpl(
    private val api: StreamsApi,
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

    companion object {
        val instance by lazy {
            StreamsRepositoryImpl(
                api = StreamsApi.create(
                    retrofit = NetworkModule.retrofit
                )
            )
        }
    }
}
