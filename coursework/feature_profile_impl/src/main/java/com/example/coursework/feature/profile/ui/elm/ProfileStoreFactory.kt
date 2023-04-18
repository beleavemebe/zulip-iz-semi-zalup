package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.feature.profile.ui.di.ProfileScope
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import com.example.coursework.shared.profile.domain.usecase.GetUser
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@ProfileScope
class ProfileStoreFactory @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val getUser: GetUser,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = ProfileState(),
            reducer = ProfileReducer(),
            actor = ProfileActor(getCurrentUser, getUser)
        )
    }
}
