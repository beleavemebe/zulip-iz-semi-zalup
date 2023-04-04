package com.example.coursework.shared.profile.data.model

@kotlinx.serialization.Serializable
data class AllUsersPresenceResponse(
    val presences: Map<String, Map<String, PresenceDto>>
)
