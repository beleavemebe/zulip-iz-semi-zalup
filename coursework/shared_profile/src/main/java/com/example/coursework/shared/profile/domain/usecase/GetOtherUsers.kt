package com.example.coursework.feature.people.domain

import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UsersRepository

class GetOtherUsers(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(): List<User> {
        return usersRepository.getUsers()
    }
}
