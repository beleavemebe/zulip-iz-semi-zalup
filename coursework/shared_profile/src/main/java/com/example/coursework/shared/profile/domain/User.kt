package com.example.coursework.shared.profile.domain

data class User(
    val id: String,
    val name: String,
    val email: String,
    val status: String,
    val onlineStatus: OnlineStatus,
    val imageUrl: String,
) {
    enum class OnlineStatus {
        ONLINE, OFFLINE
    }
}
