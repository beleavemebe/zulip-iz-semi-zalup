package com.example.feature.topic.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit

class TopicDeps(
    val retrofit: Retrofit,
    val getCurrentUser: GetCurrentUser,
    val daoProvider: DaoProvider,
    val globalCicerone: Cicerone<Router>
) : BaseDeps
