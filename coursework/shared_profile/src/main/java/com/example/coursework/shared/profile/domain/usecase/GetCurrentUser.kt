package com.example.coursework.feature.profile.domain

import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.UsersRepository
import java.util.UUID

class GetCurrentUser(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(): User {
        return usersRepository.getSelf()
    }
}
