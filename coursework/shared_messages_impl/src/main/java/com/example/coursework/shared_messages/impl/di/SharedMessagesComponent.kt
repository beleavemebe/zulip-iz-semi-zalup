package com.example.coursework.shared_messages.impl.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.shared_messages.api.SharedMessagesApi
import com.example.coursework.shared_messages.api.SharedMessagesDeps
import dagger.Component

@SharedMessagesScope
@Component(
    dependencies = [SharedMessagesDeps::class],
    modules = [SharedMessagesModule::class]
)
interface SharedMessagesComponent : DaggerComponent, SharedMessagesApi {

    @Component.Factory
    interface Factory {
        fun create(deps: SharedMessagesDeps): SharedMessagesComponent
    }
}
