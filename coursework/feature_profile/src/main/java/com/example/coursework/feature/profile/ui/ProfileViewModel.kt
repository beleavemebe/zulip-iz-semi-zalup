package com.example.coursework.feature.profile.ui

import androidx.lifecycle.ViewModel
import com.example.coursework.feature.profile.ui.elm.ProfileEffect
import com.example.coursework.feature.profile.ui.elm.ProfileEvent
import com.example.coursework.feature.profile.ui.elm.ProfileState
import com.example.coursework.feature.profile.ui.elm.ProfileStoreFactory
import vivid.money.elmslie.android.storeholder.StoreHolder

class ProfileViewModel : ViewModel(), StoreHolder<ProfileEvent, ProfileEffect, ProfileState> {
    override val isStarted = true

    override val store = ProfileStoreFactory().store
}
