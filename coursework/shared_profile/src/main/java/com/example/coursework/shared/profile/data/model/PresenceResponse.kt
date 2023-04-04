package com.example.coursework.shared.profile.data.model

@kotlinx.serialization.Serializable
data class PresenceResponse(
    val aggregated: PresenceDto
)
