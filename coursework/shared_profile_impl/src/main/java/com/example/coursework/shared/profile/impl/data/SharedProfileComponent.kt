package com.example.coursework.shared.profile.impl.data

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.core.network.NetworkModule
import com.example.shared.profile.api.SharedProfileApi
import com.example.shared.profile.api.SharedProfileDeps
import dagger.Component

@Component(
    dependencies = [SharedProfileDeps::class],
    modules = [NetworkModule::class, SharedProfileModule::class]
)
interface SharedProfileComponent : DaggerComponent, SharedProfileApi {
    @Component.Factory
    interface Factory {
        fun create(
            deps: SharedProfileDeps
        ): SharedProfileComponent
    }
}
