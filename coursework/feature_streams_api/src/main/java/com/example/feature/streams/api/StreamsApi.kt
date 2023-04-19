package com.example.feature.streams.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface StreamsApi : BaseApi {
    fun getStreamsScreen(): FragmentScreen
}
