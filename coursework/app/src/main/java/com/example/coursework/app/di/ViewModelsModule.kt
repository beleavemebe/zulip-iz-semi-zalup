package com.example.coursework.app.di

import androidx.lifecycle.ViewModel
import com.example.coursework.feature.profile.ui.ProfileViewModel
import com.example.coursework.feature.profile.ui.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {
    @[Binds IntoMap ViewModelKey(ProfileViewModel::class)]
    fun bindProfileViewModel(vm: ProfileViewModel): ViewModel
}
