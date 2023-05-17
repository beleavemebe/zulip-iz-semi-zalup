package com.example.feature.create_stream.impl.ui.elm

import vivid.money.elmslie.coroutines.ElmStoreCompat
import javax.inject.Inject

class CreateStreamStoreFactory @Inject constructor(
    reducer: CreateStreamReducer,
    actor: CreateStreamActor,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = CreateStreamState(),
            reducer = reducer,
            actor = actor
        )
    }
}
