package com.example.coursework.main.di

import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.main.MainFragment
import com.example.feature.main.api.MainDeps
import dagger.Component

@MainScope
@Component(dependencies = [MainDeps::class])
interface MainComponent : DaggerComponent {
    fun inject(mainFragment: MainFragment)

    @Component.Factory
    interface Factory {
        fun create(deps: MainDeps): MainComponent
    }
}
