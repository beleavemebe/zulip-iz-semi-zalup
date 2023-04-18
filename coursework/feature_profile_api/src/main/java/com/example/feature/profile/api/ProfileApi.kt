package com.example.feature.profile.api

import com.example.coursework.core.di.BaseApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface ProfileApi : BaseApi {
    fun getProfileScreen(userId: Int?): FragmentScreen
}
