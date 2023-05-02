package com.example.feature.streams.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import retrofit2.Retrofit

interface StreamsDeps : BaseDeps {
    val retrofit: Retrofit
    val globalCicerone: Cicerone<Router>
    val daoProvider: DaoProvider

    fun getTopicScreen(stream: Int, topic: String): FragmentScreen
}
