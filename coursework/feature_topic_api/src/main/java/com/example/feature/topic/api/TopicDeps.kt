package com.example.feature.topic.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import retrofit2.Retrofit

interface TopicDeps : BaseDeps {
    val retrofit: Retrofit
    val getCurrentUser: GetCurrentUser
    val daoProvider: DaoProvider
}
