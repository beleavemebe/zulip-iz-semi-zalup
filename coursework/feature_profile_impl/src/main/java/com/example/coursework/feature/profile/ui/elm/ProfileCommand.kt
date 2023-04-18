package com.example.coursework.feature.profile.ui.elm

sealed interface ProfileCommand {
    object LoadCurrentUser : ProfileCommand
    data class LoadUser(val userId: Int) : ProfileCommand
}
