package com.example.coursework.topic.impl.data.model.dto

@kotlinx.serialization.Serializable
data class ReactionDto(
    val emoji_code: String,
    val emoji_name: String,
    val reaction_type: String,
    val user: UserDto,
    val user_id: Int
)
