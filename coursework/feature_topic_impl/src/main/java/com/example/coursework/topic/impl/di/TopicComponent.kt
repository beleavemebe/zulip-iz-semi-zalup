package com.example.coursework.topic.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.core.network.NetworkModule
import com.example.coursework.topic.impl.ui.TopicFragment
import com.example.feature.topic.api.TopicDeps
import dagger.Component

@TopicScope
@Component(
    dependencies = [TopicDeps::class],
    modules = [NetworkModule::class, TopicModule::class]
)
interface TopicComponent : DaggerComponent {
    fun inject(topicFragment: TopicFragment)

    @Component.Factory
    interface Factory {
        fun create(deps: TopicDeps): TopicComponent
    }
}
