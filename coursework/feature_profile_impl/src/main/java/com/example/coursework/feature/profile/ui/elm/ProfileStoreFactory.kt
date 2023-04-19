package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.feature.profile.ui.di.ProfileScope
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@ProfileScope
class ProfileStoreFactory @Inject constructor(
    reducer: ProfileReducer,
    actor: ProfileActor,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = ProfileState(),
            reducer = reducer,
            actor = actor
        )
    }
}
