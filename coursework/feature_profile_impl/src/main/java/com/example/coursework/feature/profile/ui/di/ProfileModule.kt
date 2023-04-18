package com.example.coursework.feature.profile.ui.di

import androidx.lifecycle.ViewModel
import com.example.coursework.feature.profile.ui.ProfileViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
object ProfileModule {
    @Module
    interface Bindings {
        @[Binds IntoMap ViewModelKey(ProfileViewModel::class)]
        fun bindViewModel(viewModel: ProfileViewModel): ViewModel
    }
}
