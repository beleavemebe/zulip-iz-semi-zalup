package com.example.coursework.shared.profile.domain.usecase

import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UsersRepository

class GetUser(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(id: Int): User {
        return usersRepository.getUser(id)
    }
}
