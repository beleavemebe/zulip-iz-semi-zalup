package com.example.coursework.feature.profile.ui.elm

import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import vivid.money.elmslie.coroutines.ElmStoreCompat

class ProfileStoreFactory {
    val store by lazy {
        ElmStoreCompat(
            initialState = ProfileState(),
            reducer = ProfileReducer(),
            actor = ProfileActor(
                getCurrentUser = GetCurrentUser(UsersRepositoryImpl.instance)
            )
        )
    }
}
