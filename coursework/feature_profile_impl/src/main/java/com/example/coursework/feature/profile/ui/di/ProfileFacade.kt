package com.example.coursework.feature.profile.ui.di

import com.example.coursework.core.di.FeatureFacade
import com.example.feature.profile.api.ProfileApi
import com.example.feature.profile.api.ProfileDeps

internal object ProfileFacade : FeatureFacade<ProfileDeps, ProfileApi, ProfileComponent>() {

    override fun createComponent(deps: ProfileDeps) = DaggerProfileComponent.factory().create(deps)

    override fun createApi(deps: ProfileDeps) = ProfileApiImpl()

}
