package com.example.coursework.feature.people.impl.ui.elm

import com.example.coursework.feature.people.impl.di.PeopleScope
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@PeopleScope
class PeopleStoreFactory @Inject constructor(
    reducer: PeopleReducer,
    actor: PeopleActor
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = PeopleState(),
            reducer = reducer,
            actor = actor
        )
    }
}
