package com.example.feature.topic.api

import com.example.coursework.core.di.BaseDeps
import com.example.coursework.shared_messages.api.domain.MessageRepository
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class TopicDeps(
    val messageRepository: MessageRepository,
    val getCurrentUser: GetCurrentUser,
    val globalCicerone: Cicerone<Router>
) : BaseDeps
