package com.example.coursework.shared.profile.domain

interface UsersRepository {
    suspend fun getSelf(): User
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Int): User
    suspend fun getUserPresence(id: Int): UserPresence
}
