package com.example.coursework.feature.create_stream.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface CreateStreamApi : BaseApi {
    fun getCreateStreamScreen(): FragmentScreen
}
