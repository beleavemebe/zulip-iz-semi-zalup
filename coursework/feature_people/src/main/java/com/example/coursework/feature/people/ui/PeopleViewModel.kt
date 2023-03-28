package com.example.coursework.feature.people.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.core.utils.CacheContainer
import com.example.coursework.core.utils.cache
import com.example.coursework.feature.people.domain.GetOtherUsers
import com.example.coursework.feature.people.ui.model.PeopleState
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.shared.profile.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class PeopleViewModel : ViewModel(), CacheContainer by CacheContainer.Map() {
    private val allUsers = GetOtherUsers().execute()
    private val _state = MutableStateFlow(PeopleState())

    val state = _state.asStateFlow()
    val searchQuery = MutableStateFlow<String?>(null)

    init {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest(::updateUsers)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)

        updateUsers("")
    }

    private fun toPersonUi(user: User): PeopleUi {
        return PeopleUi(
            id = user.id,
            name = user.name,
            imageUrl = user.imageUrl,
            isOnline = user.onlineStatus == User.OnlineStatus.ONLINE,
            email = user.email
        )
    }

    private fun updateUsers(query: String) {
        val people = loadUsers(query)
        _state.value = _state.value.copy(people = people, notFound = people.isEmpty())
    }

    private fun loadUsers(
        query: String
    ): List<PeopleUi> = cache(key = query) {
        if (query.isBlank()) {
            allUsers
        } else {
            allUsers.filter { user ->
                query.lowercase() in user.name.lowercase()
            }
        }.map(::toPersonUi)
    }
}
