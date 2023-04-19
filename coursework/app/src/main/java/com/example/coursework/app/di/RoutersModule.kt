package com.example.coursework.app.di

import com.example.coursework.core.di.GlobalCicerone
import com.example.coursework.core.di.LocalCicerone
import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoutersModule {
    @[Provides Singleton GlobalCicerone]
    fun provideGlobalCicerone() = Cicerone.create()

    @[Provides Singleton LocalCicerone]
    fun provideLocalCicerone() = Cicerone.create()
}
