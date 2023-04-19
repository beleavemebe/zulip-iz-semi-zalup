package com.example.coursework.shared.profile.impl.data

import com.example.coursework.shared.profile.impl.data.api.UsersApi
import com.example.shared.profile.api.domain.User
import com.example.shared.profile.api.domain.UserPresence
import com.example.shared.profile.api.domain.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi
) : UsersRepository {
    private val mapper = UsersMapper

    override suspend fun getSelf(): User {
        return api.getOwnUser().let { userDto ->
            mapper.toUser(userDto, null)
        }.copy(
            presence = UserPresence.ACTIVE
        )
    }

    override suspend fun getUsers(): List<User> {
        val presences = api.getPresenceOfAllUsers().presences
        return api.getUsers().members.map { userDto ->
            val presence = presences[userDto.email]?.get("aggregated")
            mapper.toUser(userDto, presence)
        }
    }

    override suspend fun getUser(id: Int): User {
        val presence = api.getUserPresence(id).aggregated
        return api.getUser(id).user.let { userDto ->
            mapper.toUser(userDto, presence)
        }
    }

    override suspend fun getUserPresence(id: Int): UserPresence {
        return api.getUserPresence(id).aggregated.let(mapper::toUserPresence)
    }
}
