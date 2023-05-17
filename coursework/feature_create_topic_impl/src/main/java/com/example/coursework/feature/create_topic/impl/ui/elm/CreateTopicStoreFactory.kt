package com.example.coursework.feature.create_topic.impl.ui.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

class CreateTopicStoreFactory @Inject constructor(
    reducer: CreateTopicReducer,
    actor: CreateTopicActor,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = CreateTopicState(),
            reducer = reducer,
            actor = actor
        )
    }
}
