package com.example.coursework.shared_streams.api.domain.repository

import com.example.coursework.shared_streams.api.domain.model.Stream
import com.example.coursework.shared_streams.api.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {
    fun getAllStreams(forceRemote: Boolean): Flow<List<Stream>>
    fun getSubscribedStreams(forceRemote: Boolean): Flow<List<Stream>>
    fun getTopics(streamId: Int): Flow<List<Topic>>

    suspend fun createStream(name: String)
}
