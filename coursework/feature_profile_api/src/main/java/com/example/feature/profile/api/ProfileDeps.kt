package com.example.feature.profile.api

import com.example.coursework.core.di.BaseDeps
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.example.shared.profile.api.domain.usecase.GetUser

interface ProfileDeps : BaseDeps {
    val getUser: GetUser
    val getCurrentUser: GetCurrentUser
}
