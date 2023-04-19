package com.example.coursework.feature.streams.impl.domain.usecase

import com.example.coursework.feature.streams.impl.domain.model.Topic
import com.example.coursework.feature.streams.impl.domain.repository.StreamsRepository
import javax.inject.Inject

class GetTopicsForStream @Inject constructor(
    private val streamsRepository: StreamsRepository
) {
    suspend fun execute(streamId: Int): List<Topic> {
        return streamsRepository.getTopics(streamId)
    }
}
