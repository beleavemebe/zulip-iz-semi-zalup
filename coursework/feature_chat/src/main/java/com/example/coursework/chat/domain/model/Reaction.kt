package com.example.coursework.chat.domain.model

data class Reaction(
    val emojiCode: String,
    val emojiName: String,
    val userId: Int
)
