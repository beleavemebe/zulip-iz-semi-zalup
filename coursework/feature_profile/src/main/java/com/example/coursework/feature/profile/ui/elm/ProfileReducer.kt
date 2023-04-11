package com.example.coursework.feature.profile.ui.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class ProfileReducer : DslReducer<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>() {
    override fun Result.reduce(event: ProfileEvent) =
        when (event) {
            is ProfileEvent.Init -> loadUser()
            is ProfileEvent.Internal.UserLoaded -> showUser(event)
            is ProfileEvent.Internal.CaughtError -> showError(event)
        }

    private fun Result.loadUser() {
        commands {
            +ProfileCommand.LoadUser
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
