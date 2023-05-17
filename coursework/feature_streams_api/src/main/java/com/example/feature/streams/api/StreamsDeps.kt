package com.example.feature.streams.api

import com.example.coursework.core.di.BaseDeps
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface StreamsDeps : BaseDeps {
    val globalCicerone: Cicerone<Router>
    val streamsRepository: StreamsRepository

    fun getTopicScreen(
        streamId: Int,
        stream: String,
        topic: String
    ): FragmentScreen

    fun getCreateStreamScreen(): FragmentScreen

    fun getCreateTopicScreen(streamId: Int, stream: String): FragmentScreen
}
