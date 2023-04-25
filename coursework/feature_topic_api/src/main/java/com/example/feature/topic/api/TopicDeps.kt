package com.example.feature.topic.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import com.example.shared.profile.api.domain.usecase.GetCurrentUser

interface TopicDeps : BaseDeps {
    val getCurrentUser: GetCurrentUser
    val daoProvider: DaoProvider
}
