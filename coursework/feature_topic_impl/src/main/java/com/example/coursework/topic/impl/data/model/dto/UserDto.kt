package com.example.coursework.topic.impl.data.model.dto

@kotlinx.serialization.Serializable
data class UserDto(
    val email: String,
    val full_name: String,
    val id: Int
)
