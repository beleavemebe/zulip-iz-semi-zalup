@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
package com.example.coursework.feature.people.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.core.utils.cache
import com.example.coursework.feature.people.domain.GetOtherUsers
import com.example.coursework.feature.people.ui.model.PeopleState
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.shared.profile.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PeopleViewModel : BaseViewModel() {
    private val _state = MutableStateFlow(PeopleState())

    val state = _state.asStateFlow()
    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
        coroutineScope.launch {
            updateUsers("")
        }
    }

    private fun observeSearchQuery() {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest(::updateUsers)
            .flowOn(Dispatchers.Default)
            .launchIn(coroutineScope)
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        _state.value = _state.value.copy(
            isLoading = false,
            error = throwable
        )
    }

    private suspend fun updateUsers(query: String) {
        val people = loadUsers(query)
        _state.value = _state.value.copy(
            isLoading = false,
            people = people,
            notFound = people.isEmpty(),
            error = null
        )
    }

    private suspend fun loadUsers(
        query: String
    ): List<PeopleUi> = cache(key = query) {
        val allUsers = getOtherUsers()
        if (query.isBlank()) {
            allUsers
        } else {
            allUsers.filter { user ->
                query.lowercase() in user.name.lowercase()
            }
        }.map(::toPersonUi)
    }

    private suspend fun getOtherUsers() = GetOtherUsers().execute()

    private fun toPersonUi(
        user: User
    ) = PeopleUi(
        id = user.id,
        name = user.name,
        imageUrl = user.imageUrl,
        isOnline = user.onlineStatus == User.OnlineStatus.ONLINE,
        email = user.email
    )
}
