package com.example.coursework.feature.profile.ui

import androidx.lifecycle.ViewModel
import com.example.coursework.feature.profile.ui.elm.ProfileStoreFactory
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    factory: ProfileStoreFactory
) : ViewModel() {
    val store = factory.store
}
