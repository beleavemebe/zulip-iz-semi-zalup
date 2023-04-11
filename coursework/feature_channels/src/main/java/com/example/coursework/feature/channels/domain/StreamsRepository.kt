package com.example.coursework.feature.channels.domain

interface StreamsRepository {
    suspend fun getAllStreams(): List<Stream>
    suspend fun getSubscribedStreams(): List<Stream>
    suspend fun getTopics(streamId: Int): List<Topic>
}
