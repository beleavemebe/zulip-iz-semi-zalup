package com.example.coursework.shared_messages.impl

import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.shared_messages.api.SharedMessagesApi
import com.example.coursework.shared_messages.api.SharedMessagesDeps
import com.example.coursework.shared_messages.impl.di.DaggerSharedMessagesComponent
import com.example.coursework.shared_messages.impl.di.SharedMessagesComponent

object SharedMessagesFacade : FeatureFacade<SharedMessagesDeps, SharedMessagesApi, SharedMessagesComponent>() {
    override fun createComponent(deps: SharedMessagesDeps): SharedMessagesComponent {
        return DaggerSharedMessagesComponent.factory().create(deps)
    }

    override fun createApi(
        component: SharedMessagesComponent,
        deps: SharedMessagesDeps,
    ): SharedMessagesComponent {
        return component
    }
}
