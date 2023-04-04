package com.example.coursework.shared.profile.domain.usecase

import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UsersRepository

class GetCurrentUser(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(): User {
        return usersRepository.getSelf()
    }
}
