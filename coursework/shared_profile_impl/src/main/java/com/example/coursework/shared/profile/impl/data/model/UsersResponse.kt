package com.example.coursework.shared.profile.impl.data.model

@kotlinx.serialization.Serializable
data class UsersResponse(
    val members: List<UserDto>
)
