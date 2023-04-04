package com.example.coursework.shared.profile.domain

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val presence: UserPresence,
    val imageUrl: String,
)
