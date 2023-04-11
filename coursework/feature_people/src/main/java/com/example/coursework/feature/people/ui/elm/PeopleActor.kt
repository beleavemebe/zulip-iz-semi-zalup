package com.example.coursework.feature.people.ui.elm

import com.example.coursework.core.utils.CacheContainer
import com.example.coursework.core.utils.cache
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.usecase.GetOtherUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import vivid.money.elmslie.coroutines.Actor

class PeopleActor(
    private val getOtherUsers: GetOtherUsers,
) : Actor<PeopleCommand, PeopleEvent>, CacheContainer by CacheContainer.Map() {
    override fun execute(command: PeopleCommand) =
        when (command) {
            is PeopleCommand.LoadPeople -> loadPeople()
            is PeopleCommand.FindPeople -> findPeople(command)
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = { throwable -> PeopleEvent.Internal.CaughtError(throwable) }
        )

    private fun loadPeople() = flow {
        val people = loadUsers()
        emit(PeopleEvent.Internal.PeopleLoaded(people))
    }

    private fun findPeople(command: PeopleCommand.FindPeople) =
        flow {
            val people = loadUsers(query = command.query)
            emit(PeopleEvent.Internal.PeopleLoaded(people))
        }

    private suspend fun loadUsers(
        query: String = "",
    ): List<PeopleUi> = cache(key = query) {
        withContext(Dispatchers.Default) {
            val allUsers = getOtherUsers.execute()
            if (query.isBlank()) {
                allUsers
            } else {
                allUsers.filter { user ->
                    query.lowercase() in user.name.lowercase()
                }
            }.map(::toPersonUi).sortedByPresence()
        }
    }

    private fun toPersonUi(
        user: User,
    ) = PeopleUi(
        id = user.id,
        name = user.name,
        imageUrl = user.imageUrl,
        presence = user.presence,
        email = user.email
    )

    private fun List<PeopleUi>.sortedByPresence() = sortedWith { o1, o2 ->
        o1.presence.ordinal - o2.presence.ordinal
    }
}
