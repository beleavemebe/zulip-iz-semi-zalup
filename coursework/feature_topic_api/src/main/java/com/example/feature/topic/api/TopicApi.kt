package com.example.feature.topic.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface TopicApi : BaseApi {
    fun getTopicScreen(
        streamId: Int,
        stream: String,
        topic: String
    ): FragmentScreen
}
