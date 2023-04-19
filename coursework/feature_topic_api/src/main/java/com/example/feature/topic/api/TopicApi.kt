package com.example.feature.topic.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface TopicApi : BaseApi {
    fun getTopicScreen(stream: Int, topic: String): FragmentScreen
}
