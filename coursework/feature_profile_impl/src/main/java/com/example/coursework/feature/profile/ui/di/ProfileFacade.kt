package com.example.coursework.feature.profile.ui.di

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.feature.profile.ui.ProfileFragment
import com.example.coursework.feature.profile.ui.elm.ProfileEvent
import com.example.feature.profile.api.ProfileApi
import com.example.feature.profile.api.ProfileDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

object ProfileFacade : FeatureFacade<ProfileDeps, ProfileApi, ProfileComponent>() {

    override fun createComponent(deps: ProfileDeps) = DaggerProfileComponent.factory().create(deps)

    override fun createApi(component: ProfileComponent, deps: ProfileDeps) = object : ProfileApi {
        override fun getProfileScreen(userId: Int?) = FragmentScreen {
            ProfileFragment.newInstance(userId ?: ProfileEvent.Init.CURRENT_USER)
        }
    }

}
