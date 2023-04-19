package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.di.TopicScope
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@TopicScope
class TopicStoreFactory @Inject constructor(
    reducer: TopicReducer,
    actor: TopicActor,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = TopicState(),
            reducer = reducer,
            actor = actor
        )
    }
}
