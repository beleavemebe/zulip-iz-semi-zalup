package com.example.coursework.feature.streams.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.feature.streams.impl.ui.StreamsFragment
import com.example.feature.streams.api.StreamsDeps
import dagger.Component

@StreamsScope
@Component(
    dependencies = [StreamsDeps::class],
)
interface StreamsComponent : DaggerComponent {
    fun inject(streamsFragment: StreamsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            deps: StreamsDeps
        ): StreamsComponent
    }
}
