package com.example.shared_streams.impl.coursework.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.shared_streams.api.SharedStreamsApi
import com.example.coursework.shared_streams.api.SharedStreamsDeps
import dagger.Component

@SharedStreamsScope
@Component(
    dependencies = [SharedStreamsDeps::class],
    modules = [SharedStreamsModule::class]
)
interface SharedStreamsComponent : DaggerComponent, SharedStreamsApi {
    @Component.Factory
    interface Factory {
        fun create(deps: SharedStreamsDeps): SharedStreamsComponent
    }
}
