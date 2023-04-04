package com.example.coursework.shared.profile.domain.usecase

import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UsersRepository

class GetOtherUsers(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(): List<User> {
        return usersRepository.getUsers()
    }
}
