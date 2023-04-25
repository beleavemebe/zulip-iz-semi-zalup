package com.example.coursework.app.database.di

import com.example.coursework.core.di.DaggerComponent
import dagger.Component

@Component(
    dependencies = [AppDbDeps::class],
    modules = [AppDbModule::class]
)
interface AppDbComponent : DaggerComponent, AppDbApi {
    @Component.Factory
    interface Factory {
        fun create(deps: AppDbDeps): AppDbComponent
    }
}
