package com.example.coursework.topic.impl.ui.model

data class ReactionUi(
    val emote: String,
    val name: String,
    val reactionCount: Int = 1,
    val isPressed: Boolean = false,
)
