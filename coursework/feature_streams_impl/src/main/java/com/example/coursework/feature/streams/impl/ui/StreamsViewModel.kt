@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coursework.feature.streams.impl.ui

import androidx.lifecycle.viewModelScope
import com.example.core.ui.base.BaseViewModel
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.feature.streams.impl.ui.elm.StreamsEvent
import com.example.coursework.feature.streams.impl.ui.elm.StreamsStoreFactory
import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab
import com.example.coursework.feature.streams.impl.ui.model.TopicUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

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

    fun clickChat(topicUi: TopicUi) {
        store.accept(StreamsEvent.Ui.ClickTopic(topicUi))
    }

    override fun onCleared() {
        super.onCleared()
        StreamsFacade.clear()
    }
}
