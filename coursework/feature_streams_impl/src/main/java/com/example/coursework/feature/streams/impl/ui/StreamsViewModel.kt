@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coursework.feature.streams.impl.ui

import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseViewModel
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.feature.streams.impl.ui.elm.StreamsEvent
import com.example.coursework.feature.streams.impl.ui.elm.StreamsStoreFactory
import com.example.coursework.feature.streams.impl.ui.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.lang.ref.WeakReference

class StreamsViewModel(
    storeFactory: StreamsStoreFactory
): BaseViewModel() {
    val store = storeFactory.store

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
            .launchIn(viewModelScope)
    }

    fun selectStreamsTab(tab: StreamsTab) {
        store.accept(StreamsEvent.Ui.SelectStreamsTab(tab))
    }

    fun clickStream(streamUi: StreamUi) {
        store.accept(StreamsEvent.Ui.ClickStream(streamUi))
    }

    fun clickTopic(topicUi: TopicUi) {
        store.accept(StreamsEvent.Ui.ClickTopic(topicUi))
    }

    fun clickCreateStream() {
        store.accept(StreamsEvent.Ui.ClickCreateStream)
        StreamsFacade.onStreamCreated = WeakReference(::onStreamCreated)
    }

    private fun onStreamCreated(name: String) {
        store.accept(StreamsEvent.Internal.StreamCreated(name))
    }

    fun clickViewAllMessages(viewAllMessagesUi: ViewAllMessagesUi) {
        store.accept(StreamsEvent.Ui.ClickViewAllMessages)
    }

    fun clickCreateTopic(createTopicUi: CreateTopicUi) {
        store.accept(StreamsEvent.Ui.ClickCreateTopic(createTopicUi.streamUi))
    }

    override fun onCleared() {
        super.onCleared()
        StreamsFacade.clear()
    }
}
