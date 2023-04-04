package com.example.coursework.shared.profile.data.api.model

@kotlinx.serialization.Serializable
data class UsersResponse(
    val members: List<UserDto>
)
