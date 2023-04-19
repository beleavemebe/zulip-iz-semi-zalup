package com.example.coursework.shared.profile.impl.data.model

@kotlinx.serialization.Serializable
data class PresenceDto(
    val status: String,
    val timestamp: Long,
)
