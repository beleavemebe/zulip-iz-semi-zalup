package com.example.coursework.chat.ui.view

data class MessageReaction(
    val emote: String,
    val reactions: Int = 1,
    val isPressed: Boolean = false,
)
