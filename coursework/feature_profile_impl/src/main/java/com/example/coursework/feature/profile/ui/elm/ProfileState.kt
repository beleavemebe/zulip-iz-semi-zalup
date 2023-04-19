package com.example.coursework.feature.profile.ui.elm

import com.example.shared.profile.api.domain.User

data class ProfileState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val error: Throwable? = null,
)
