package com.example.coursework.shared_streams.api

import com.example.coursework.core.di.BaseApi
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository

interface SharedStreamsApi : BaseApi {
    val streamsRepository: StreamsRepository
}
