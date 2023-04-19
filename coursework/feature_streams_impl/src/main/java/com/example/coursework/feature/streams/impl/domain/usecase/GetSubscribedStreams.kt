package com.example.coursework.feature.streams.impl.domain.usecase

import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.repository.StreamsRepository
import javax.inject.Inject

class GetSubscribedStreams @Inject constructor(
    private val streamsRepository: StreamsRepository
) {
    suspend fun execute(): List<Stream> {
        return streamsRepository.getSubscribedStreams()
    }
}
