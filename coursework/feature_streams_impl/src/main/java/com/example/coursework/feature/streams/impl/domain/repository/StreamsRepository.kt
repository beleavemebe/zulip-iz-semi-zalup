package com.example.coursework.feature.streams.impl.domain.repository

import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic

interface StreamsRepository {
    suspend fun getAllStreams(): List<Stream>
    suspend fun getSubscribedStreams(): List<Stream>
    suspend fun getTopics(streamId: Int): List<Topic>
}
