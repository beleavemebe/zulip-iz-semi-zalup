package com.example.coursework.feature.streams.impl.domain.repository

import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {
    suspend fun getAllStreams(): Flow<List<Stream>>
    suspend fun getSubscribedStreams(): Flow<List<Stream>>
    suspend fun getTopics(streamId: Int): Flow<List<Topic>>
}
