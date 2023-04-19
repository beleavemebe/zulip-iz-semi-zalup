package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.di.StreamsScope
import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

@StreamsScope
class StreamsStoreFactory @Inject constructor(
    reducer: StreamsReducer,
    actor: StreamsActor,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = StreamsState(),
            reducer = reducer,
            actor = actor
        )
    }
}
