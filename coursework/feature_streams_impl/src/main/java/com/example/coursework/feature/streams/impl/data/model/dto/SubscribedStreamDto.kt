package com.example.coursework.feature.streams.impl.data.model.dto

@kotlinx.serialization.Serializable
data class SubscribedStreamDto(
    val audible_notifications: Boolean?,
    val color: String,
    val description: String,
    val desktop_notifications: Boolean?,
    val email_address: String,
    val invite_only: Boolean,
    val is_muted: Boolean,
    val name: String,
    val pin_to_top: Boolean,
    val push_notifications: Boolean?,
    val stream_id: Int,
    val subscribers: List<Int> = emptyList()
)
