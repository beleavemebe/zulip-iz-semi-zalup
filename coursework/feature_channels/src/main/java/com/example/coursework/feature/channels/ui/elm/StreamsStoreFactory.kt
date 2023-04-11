package com.example.coursework.feature.channels.ui.elm

import com.example.coursework.feature.channels.domain.GetAllStreams
import com.example.coursework.feature.channels.domain.GetSubscribedStreams
import com.example.coursework.feature.channels.domain.GetTopicsForStream
import vivid.money.elmslie.coroutines.ElmStoreCompat

class StreamsStoreFactory(
    getAllStreams: GetAllStreams,
    getSubscribedStreams: GetSubscribedStreams,
    getTopicsForStream: GetTopicsForStream,
) {
    val store by lazy {
        ElmStoreCompat(
            initialState = StreamsState(),
            reducer = StreamsReducer(),
            actor = StreamsActor(getAllStreams, getSubscribedStreams, getTopicsForStream)
        )
    }
}
