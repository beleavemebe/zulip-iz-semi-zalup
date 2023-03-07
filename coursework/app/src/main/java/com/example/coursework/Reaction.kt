package com.example.coursework

data class Reaction(
    val emote: String,
    val reactions: Int = 1,
    val isPressed: Boolean = false,
)
