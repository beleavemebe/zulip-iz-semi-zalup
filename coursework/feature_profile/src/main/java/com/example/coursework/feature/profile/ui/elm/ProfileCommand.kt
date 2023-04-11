package com.example.coursework.feature.profile.ui.elm

sealed interface ProfileCommand {
    object LoadUser : ProfileCommand
}
