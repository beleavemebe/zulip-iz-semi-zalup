package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

class ProfileActor(
    private val getCurrentUser: GetCurrentUser,
) : Actor<ProfileCommand, ProfileEvent> {
    override fun execute(command: ProfileCommand) =
        when (command) {
            is ProfileCommand.LoadUser -> loadUser()
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = ProfileEvent.Internal::CaughtError
        )

    private fun loadUser() = flow {
        val user = getCurrentUser.execute()
        emit(ProfileEvent.Internal.UserLoaded(user))
    }
}
