package com.example.coursework.shared.profile.data.model

@kotlinx.serialization.Serializable
data class PresenceDto(
    val status: String,
    val timestamp: Long,
)
