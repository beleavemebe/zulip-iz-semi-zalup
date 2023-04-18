package com.example.feature.profile.api

import com.example.coursework.core.di.BaseDeps
import com.example.coursework.core.di.DaggerViewModelFactory
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import com.example.coursework.shared.profile.domain.usecase.GetUser

interface ProfileDeps : BaseDeps {
    val getUser: GetUser
    val getCurrentUser: GetCurrentUser
    val daggerViewModelFactory: DaggerViewModelFactory
}
