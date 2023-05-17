package com.example.feature.create_topic.api

import com.example.coursework.core.di.BaseDeps
import com.example.coursework.shared_messages.api.domain.MessageRepository
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

interface CreateTopicDeps : BaseDeps {
    val globalCicerone: Cicerone<Router>
    val streamsRepository: StreamsRepository
    val messageRepository: MessageRepository

    fun onTopicCreated(streamId:Int, stream: String, topic: String)
}
