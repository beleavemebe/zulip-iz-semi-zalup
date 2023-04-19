package com.example.coursework.shared.profile.impl.data.model

@kotlinx.serialization.Serializable
data class AllUsersPresenceResponse(
    val presences: Map<String, Map<String, PresenceDto>>
)
