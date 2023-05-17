package com.example.coursework.feature.create_topic.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.feature.create_topic.impl.di.CreateTopicComponent
import com.example.coursework.feature.create_topic.impl.di.DaggerCreateTopicComponent
import com.example.coursework.feature.create_topic.impl.ui.CreateTopicFragment
import com.example.feature.create_topic.api.CreateTopicApi
import com.example.feature.create_topic.api.CreateTopicDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

object CreateTopicFacade : FeatureFacade<CreateTopicDeps, CreateTopicApi, CreateTopicComponent>() {
    override fun createComponent(deps: CreateTopicDeps): CreateTopicComponent {
        return DaggerCreateTopicComponent.factory().create(deps)
    }

    override fun createApi(
        component: CreateTopicComponent,
        deps: CreateTopicDeps,
    ): CreateTopicApi {
        return object : CreateTopicApi {
            override fun getCreateTopicScreen(streamId: Int, stream: String) = FragmentScreen {
                CreateTopicFragment.newInstance(streamId, stream)
            }
        }
    }
}
