package com.example.coursework.feature.streams.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.feature.streams.impl.di.DaggerStreamsComponent
import com.example.coursework.feature.streams.impl.di.StreamsComponent
import com.example.coursework.feature.streams.impl.ui.StreamsFragment
import com.example.feature.streams.api.StreamsApi
import com.example.feature.streams.api.StreamsDeps
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.lang.ref.WeakReference

object StreamsFacade : FeatureFacade<StreamsDeps, StreamsApi, StreamsComponent>() {
    override fun createComponent(deps: StreamsDeps): StreamsComponent {
        return DaggerStreamsComponent.factory().create(deps)
    }

    override fun createApi(component: StreamsComponent, deps: StreamsDeps): StreamsApi {
        return object : StreamsApi {
            override fun getStreamsScreen() = FragmentScreen {
                StreamsFragment()
            }
        }
    }

    var onStreamCreated: WeakReference<(name: String) -> Unit>? = null
        internal set
}
