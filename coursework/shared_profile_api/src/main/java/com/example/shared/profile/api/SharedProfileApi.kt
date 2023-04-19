package com.example.shared.profile.api

import com.example.coursework.core.di.BaseApi
import com.example.shared.profile.api.domain.UsersRepository

interface SharedProfileApi : BaseApi {
    val usersRepository: UsersRepository
}
