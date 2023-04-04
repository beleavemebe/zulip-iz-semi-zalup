package com.example.coursework.shared.profile.data

import com.example.coursework.core.network.NetworkModule
import com.example.coursework.shared.profile.data.api.UsersApi
import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UserPresence
import com.example.coursework.shared.profile.domain.UsersRepository

class UsersRepositoryImpl(
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

    companion object {
        val instance by lazy {
            UsersRepositoryImpl(
                api = UsersApi.create(retrofit = NetworkModule.retrofit)
            )
        }
    }
}
