package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.shared.profile.domain.User

sealed interface ProfileEvent {
    data class Init(val userId: Int) : ProfileEvent {
        companion object {
            const val CURRENT_USER = -1
        }
    }

    sealed interface Internal : ProfileEvent {
        data class UserLoaded(val user: User): Internal
        data class CaughtError(val error: Throwable): Internal
    }
}
