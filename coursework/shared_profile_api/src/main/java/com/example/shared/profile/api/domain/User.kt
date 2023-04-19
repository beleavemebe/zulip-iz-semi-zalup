package com.example.shared.profile.api.domain

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val presence: UserPresence,
    val imageUrl: String,
)
