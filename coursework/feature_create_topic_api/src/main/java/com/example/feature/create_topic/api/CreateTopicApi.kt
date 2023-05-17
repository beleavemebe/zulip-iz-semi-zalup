package com.example.feature.create_topic.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface CreateTopicApi : BaseApi {
    fun getCreateTopicScreen(streamId: Int, stream: String): FragmentScreen
}
