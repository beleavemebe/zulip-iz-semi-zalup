package com.example.coursework.topic.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.topic.impl.di.DaggerTopicComponent
import com.example.coursework.topic.impl.di.TopicComponent
import com.example.coursework.topic.impl.ui.TopicFragment
import com.example.feature.topic.api.TopicApi
import com.example.feature.topic.api.TopicDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen

object TopicFacade : FeatureFacade<TopicDeps, TopicApi, TopicComponent>() {
    override fun createComponent(deps: TopicDeps): TopicComponent {
        return DaggerTopicComponent.factory().create(deps)
    }

    override fun createApi(component: TopicComponent, deps: TopicDeps): TopicApi {
        return object : TopicApi {
            override fun getTopicScreen(
                streamId: Int,
                stream: String,
                topic: String,
            ) = FragmentScreen {
                TopicFragment.newInstance(streamId, stream, topic)
            }
        }
    }
}
