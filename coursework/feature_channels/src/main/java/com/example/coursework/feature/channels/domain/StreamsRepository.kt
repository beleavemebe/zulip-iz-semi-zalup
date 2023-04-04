package com.example.coursework.feature.channels.domain

import com.example.coursework.shared.chat.Topic

interface StreamsRepository {
    suspend fun getAllStreams(): List<Stream>
    suspend fun getSubscribedStreams(): List<Stream>
    suspend fun getTopics(streamId: Int): List<Topic>
}
