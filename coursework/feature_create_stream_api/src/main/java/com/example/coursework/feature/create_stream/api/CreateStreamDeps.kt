package com.example.coursework.feature.create_stream.api

import com.example.coursework.core.di.BaseDeps
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

interface CreateStreamDeps : BaseDeps {
    val globalCicerone: Cicerone<Router>
    val streamsRepository: StreamsRepository

    fun onStreamCreated(name: String)
}
