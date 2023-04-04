@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
package com.example.coursework.feature.people.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.core.utils.cache
import com.example.coursework.feature.people.ui.model.PeopleState
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.usecase.GetOtherUsers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class PeopleViewModel : BaseViewModel() {
    private val getOtherUsers = GetOtherUsers(UsersRepositoryImpl.instance)
    private val _state = MutableStateFlow(PeopleState())

    val state = _state.asStateFlow()
    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
        updateUsers("")
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

    private fun updateUsers(query: String) {
        coroutineScope.launch {
            val people = loadUsers(query)
            _state.value = _state.value.copy(
                isLoading = false,
                people = people,
                notFound = people.isEmpty(),
                error = null
            )
        }
    }

    private suspend fun loadUsers(
        query: String
    ): List<PeopleUi> = cache(key = query) {
        withContext(Dispatchers.Default) {
            val allUsers = getOtherUsers()
            if (query.isBlank()) {
                allUsers
            } else {
                allUsers.filter { user ->
                    query.lowercase() in user.name.lowercase()
                }
            }.map(::toPersonUi).sortedByPresence()
        }
    }

    private suspend fun getOtherUsers() = getOtherUsers.execute()

    private fun toPersonUi(
        user: User
    ) = PeopleUi(
        id = user.id,
        name = user.name,
        imageUrl = user.imageUrl,
        presence = user.presence,
        email = user.email
    )

    private fun List<PeopleUi>.sortedByPresence() = run {
        sortedWith { o1, o2 ->
            o1.presence.ordinal - o2.presence.ordinal
        }
    }
}

