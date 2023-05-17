package com.example.feature.create_stream.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.feature.create_stream.api.CreateStreamDeps
import com.example.feature.create_stream.impl.ui.CreateStreamFragment
import dagger.Component

@Component(dependencies = [CreateStreamDeps::class])
interface CreateStreamComponent : DaggerComponent {
    fun inject(createStreamFragment: CreateStreamFragment)

    @Component.Factory
    interface Factory {
        fun create(createStreamDeps: CreateStreamDeps): CreateStreamComponent
    }
}
