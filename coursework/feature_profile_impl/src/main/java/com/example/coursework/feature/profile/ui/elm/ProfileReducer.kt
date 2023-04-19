package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.feature.profile.ui.di.ProfileScope
import com.example.coursework.feature.profile.ui.elm.ProfileEvent.Init.Companion.CURRENT_USER
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@ProfileScope
class ProfileReducer @Inject constructor(): DslReducer<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>() {
    override fun Result.reduce(event: ProfileEvent) =
        when (event) {
            is ProfileEvent.Init -> loadUser(event)
            is ProfileEvent.Internal.UserLoaded -> showUser(event)
            is ProfileEvent.Internal.CaughtError -> showError(event)
        }

    private fun Result.loadUser(event: ProfileEvent.Init) {
        if (event.userId == CURRENT_USER) {
            commands {
                +ProfileCommand.LoadCurrentUser
            }
        } else {
            commands {
                +ProfileCommand.LoadUser(event.userId)
            }
        }
    }

    private fun Result.showUser(
        event: ProfileEvent.Internal.UserLoaded,
    ) {
        state {
            copy(isLoading = false, user = event.user, error = null)
        }
    }

    private fun Result.showError(
        event: ProfileEvent.Internal.CaughtError,
    ) {
        state {
            copy(error = event.error)
        }
    }
}
