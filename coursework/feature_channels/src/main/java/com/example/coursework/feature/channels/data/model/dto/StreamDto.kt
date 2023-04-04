package com.example.coursework.feature.channels.data.model.dto

@kotlinx.serialization.Serializable
data class StreamDto(
    val description: String,
    val invite_only: Boolean,
    val name: String,
    val stream_id: Int
)
