package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import com.example.coursework.shared.profile.domain.usecase.GetUser
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

class ProfileActor(
    private val getCurrentUser: GetCurrentUser,
    private val getUser: GetUser,
) : Actor<ProfileCommand, ProfileEvent> {
    override fun execute(command: ProfileCommand) =
        when (command) {
            is ProfileCommand.LoadCurrentUser -> loadCurrentUser()
            is ProfileCommand.LoadUser -> loadUser(command.userId)
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = ProfileEvent.Internal::CaughtError
        )

    private fun loadCurrentUser() = flow {
        val user = getCurrentUser.execute()
        emit(ProfileEvent.Internal.UserLoaded(user))
    }

    private fun loadUser(userId: Int) = flow {
        val user = getUser.execute(userId)
        emit(ProfileEvent.Internal.UserLoaded(user))
    }
}
