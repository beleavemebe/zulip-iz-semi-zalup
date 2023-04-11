@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
package com.example.coursework.feature.people.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.feature.people.ui.elm.PeopleEvent
import com.example.coursework.feature.people.ui.elm.PeopleStoreFactory
import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.usecase.GetOtherUsers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class PeopleViewModel : BaseViewModel() {
    val store = PeopleStoreFactory(
        GetOtherUsers(UsersRepositoryImpl.instance)
    ).store

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
            .launchIn(coroutineScope)
    }
}

