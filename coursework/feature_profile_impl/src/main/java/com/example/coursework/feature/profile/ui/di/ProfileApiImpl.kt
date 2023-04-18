package com.example.coursework.feature.profile.ui.di

import com.example.coursework.feature.profile.ui.ProfileFragment
import com.example.coursework.feature.profile.ui.elm.ProfileEvent.Init.Companion.CURRENT_USER
import com.example.feature.profile.api.ProfileApi
import com.github.terrakok.cicerone.androidx.FragmentScreen

class ProfileApiImpl : ProfileApi {
    override fun getProfileScreen(userId: Int?) = FragmentScreen {
        ProfileFragment.newInstance(userId ?: CURRENT_USER)
    }
}
