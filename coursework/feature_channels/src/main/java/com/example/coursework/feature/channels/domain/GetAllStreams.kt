package com.example.coursework.feature.channels.domain

class GetAllStreams(
    private val streamsRepository: StreamsRepository
) {
    suspend fun execute(): List<Stream> {
        return streamsRepository.getAllStreams()
    }
}
