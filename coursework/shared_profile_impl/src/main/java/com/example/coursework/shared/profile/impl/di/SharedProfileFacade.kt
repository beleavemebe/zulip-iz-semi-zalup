package com.example.coursework.shared.profile.impl.di

import com.example.coursework.core.di.FeatureFacade
import com.example.shared.profile.api.SharedProfileApi
import com.example.shared.profile.api.SharedProfileDeps

object SharedProfileFacade : FeatureFacade<SharedProfileDeps, SharedProfileApi, SharedProfileComponent>() {
    override fun createComponent(deps: SharedProfileDeps): SharedProfileComponent {
        return DaggerSharedProfileComponent.factory().create(deps)
    }

    override fun createApi(component: SharedProfileComponent, deps: SharedProfileDeps): SharedProfileApi {
        return component
    }
}
