package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.feature.profile.ui.di.ProfileScope
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import com.example.shared.profile.api.domain.usecase.GetUser
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@ProfileScope
class ProfileActor @Inject constructor(
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
