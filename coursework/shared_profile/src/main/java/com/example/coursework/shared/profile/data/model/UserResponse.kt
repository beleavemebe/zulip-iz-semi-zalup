package com.example.coursework.shared.profile.data.api

import com.example.coursework.shared.profile.data.api.model.UserDto

@kotlinx.serialization.Serializable
data class UserResponse(
    val user: UserDto
)
