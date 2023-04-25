package com.example.coursework.feature.streams.impl.domain.repository

import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface StreamsRepository {
    fun getAllStreams(): Flow<List<Stream>>
    fun getSubscribedStreams(): Flow<List<Stream>>
    fun getTopics(streamId: Int): Flow<List<Topic>>
}
