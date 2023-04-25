package com.example.coursework.app

import android.app.Application
import android.content.Context
import com.example.coursework.app.di.AppComponent
import com.example.coursework.app.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as App).appComponent
