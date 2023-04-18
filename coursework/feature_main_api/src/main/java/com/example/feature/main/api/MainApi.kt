package com.example.feature.main.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface MainApi : BaseApi {
    fun getMainScreen(): FragmentScreen
}

