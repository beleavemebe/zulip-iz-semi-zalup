package com.example.shared.profile.api.domain.usecase

import com.example.shared.profile.api.domain.User
import com.example.shared.profile.api.domain.UsersRepository
import javax.inject.Inject

class GetUser @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend fun execute(id: Int): User {
        return usersRepository.getUser(id)
    }
}
