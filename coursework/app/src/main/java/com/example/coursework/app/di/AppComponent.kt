package com.example.coursework.app.di

import android.content.Context
import com.example.coursework.app.MainActivity
import com.example.feature.people.api.PeopleDeps
import com.example.feature.profile.api.ProfileDeps
import com.example.feature.topic.api.TopicDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FeatureGluingModule::class, ApiUnpackingModule::class, RoutersModule::class]
)
interface AppComponent : ProfileDeps, PeopleDeps, TopicDeps {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}
