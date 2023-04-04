package com.example.coursework.shared.profile.data.model

@kotlinx.serialization.Serializable
data class UsersResponse(
    val members: List<UserDto>
)
