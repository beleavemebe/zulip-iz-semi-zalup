package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.shared.profile.domain.User

data class ProfileState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val error: Throwable? = null,
)
