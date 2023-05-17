@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
package com.example.coursework.feature.people.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.feature.people.impl.PeopleFacade
import com.example.coursework.feature.people.impl.ui.elm.PeopleEvent
import com.example.coursework.feature.people.impl.ui.elm.PeopleStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class PeopleViewModel(storeFactory: PeopleStoreFactory) : ViewModel() {
    val store = storeFactory.store

    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest(PeopleEvent.Ui::UpdateSearchQuery)
            .onEach(store::accept)
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        PeopleFacade.clear()
    }
}

