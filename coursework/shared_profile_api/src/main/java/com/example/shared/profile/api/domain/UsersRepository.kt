package com.example.shared.profile.api.domain

interface UsersRepository {
    suspend fun getSelf(): User
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Int): User
    suspend fun getUserPresence(id: Int): UserPresence
}
