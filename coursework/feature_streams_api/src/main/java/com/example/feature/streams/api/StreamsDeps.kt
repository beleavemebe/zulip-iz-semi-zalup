package com.example.feature.streams.api

import com.example.coursework.core.di.BaseDeps
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface StreamsDeps : BaseDeps {
    val globalCicerone: Cicerone<Router>
    fun getTopicScreen(stream: Int, topic: String): FragmentScreen
}