package com.example.shared_streams.impl.coursework

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.shared_streams.api.SharedStreamsApi
import com.example.coursework.shared_streams.api.SharedStreamsDeps
import com.example.shared_streams.impl.coursework.di.DaggerSharedStreamsComponent
import com.example.shared_streams.impl.coursework.di.SharedStreamsComponent

object SharedStreamsFacade : FeatureFacade<SharedStreamsDeps, SharedStreamsApi, SharedStreamsComponent>() {
    override fun createComponent(deps: SharedStreamsDeps): SharedStreamsComponent {
        return DaggerSharedStreamsComponent.factory().create(deps)
    }

    override fun createApi(component: SharedStreamsComponent, deps: SharedStreamsDeps) = component
}
