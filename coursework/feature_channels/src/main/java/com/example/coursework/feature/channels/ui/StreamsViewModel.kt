@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coursework.feature.channels.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.feature.channels.data.StreamsRepositoryImpl
import com.example.coursework.feature.channels.domain.GetAllStreams
import com.example.coursework.feature.channels.domain.GetSubscribedStreams
import com.example.coursework.feature.channels.domain.GetTopicsForStream
import com.example.coursework.feature.channels.ui.elm.StreamsEvent
import com.example.coursework.feature.channels.ui.elm.StreamsStoreFactory
import com.example.coursework.feature.channels.ui.model.StreamUi
import com.example.coursework.feature.channels.ui.model.StreamsTab
import com.example.coursework.feature.channels.ui.model.TopicUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class StreamsViewModel : BaseViewModel() {
    val store = StreamsStoreFactory(
        GetAllStreams(StreamsRepositoryImpl.instance),
        GetSubscribedStreams(StreamsRepositoryImpl.instance),
        GetTopicsForStream(StreamsRepositoryImpl.instance)
    ).store

    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest(StreamsEvent.Ui::UpdateSearchQuery)
            .onEach(store::accept)
            .launchIn(coroutineScope)
    }

    fun selectStreamsTab(tab: StreamsTab) {
        store.accept(StreamsEvent.Ui.SelectStreamsTab(tab))
    }

    fun clickStream(streamUi: StreamUi) {
        store.accept(StreamsEvent.Ui.ClickStream(streamUi))
    }

    fun clickChat(topicUi: TopicUi) {
        store.accept(StreamsEvent.Ui.ClickTopic(topicUi))
    }
}
