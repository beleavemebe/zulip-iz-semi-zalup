package com.example.coursework.feature.channels.domain

class GetTopicsForStream(
    private val streamsRepository: StreamsRepository
) {
    suspend fun execute(streamId: Int): List<Topic> {
        return streamsRepository.getTopics(streamId)
    }
}
