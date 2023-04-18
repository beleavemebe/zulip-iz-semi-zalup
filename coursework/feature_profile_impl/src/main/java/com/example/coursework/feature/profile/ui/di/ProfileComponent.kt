package com.example.coursework.feature.profile.ui.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.feature.profile.ui.ProfileFragment
import com.example.feature.profile.api.ProfileDeps
import dagger.Component

@Component(
    modules = [ProfileModule::class, ProfileModule.Bindings::class],
    dependencies = [ProfileDeps::class]
)
interface ProfileComponent : DaggerComponent {
    fun inject(profileFragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(
            deps: ProfileDeps,
        ): ProfileComponent
    }
}
