package com.example.feature.create_stream.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.feature.create_stream.api.CreateStreamApi
import com.example.coursework.feature.create_stream.api.CreateStreamDeps
import com.example.feature.create_stream.impl.di.CreateStreamComponent
import com.example.feature.create_stream.impl.di.DaggerCreateStreamComponent
import com.example.feature.create_stream.impl.ui.CreateStreamFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CreateStreamFacade : FeatureFacade<CreateStreamDeps, CreateStreamApi, CreateStreamComponent>() {
    override fun createComponent(deps: CreateStreamDeps): CreateStreamComponent {
        return DaggerCreateStreamComponent.factory().create(deps)
    }

    override fun createApi(
        component: CreateStreamComponent,
        deps: CreateStreamDeps,
    ): CreateStreamApi {
        return object : CreateStreamApi {
            override fun getCreateStreamScreen() = FragmentScreen {
                CreateStreamFragment()
            }
        }
    }
}
