package com.example.coursework.feature.people.ui.elm

import com.example.coursework.shared.profile.domain.usecase.GetOtherUsers
import vivid.money.elmslie.coroutines.ElmStoreCompat

class PeopleStoreFactory(
    getOtherUsers: GetOtherUsers
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = PeopleState(),
            reducer = PeopleReducer(),
            actor = PeopleActor(getOtherUsers)
        )
    }
}
