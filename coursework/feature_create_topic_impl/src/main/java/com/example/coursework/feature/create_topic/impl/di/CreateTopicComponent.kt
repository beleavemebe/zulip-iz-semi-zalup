package com.example.coursework.feature.create_topic.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.feature.create_topic.impl.ui.CreateTopicFragment
import com.example.feature.create_topic.api.CreateTopicDeps
import dagger.Component

@Component(dependencies = [CreateTopicDeps::class])
interface CreateTopicComponent : DaggerComponent {
    fun inject(createTopicFragment: CreateTopicFragment)

    @Component.Factory
    interface Factory {
        fun create(createTopicDeps: CreateTopicDeps): CreateTopicComponent
    }
}
