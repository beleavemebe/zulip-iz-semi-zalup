package com.example.coursework.feature.channels.domain

class GetSubscribedStreams(
    private val streamsRepository: StreamsRepository
) {
    suspend fun execute(): List<Stream> {
        return streamsRepository.getSubscribedStreams()
    }
}
