package com.example.coursework.shared.chat

import android.graphics.Color

data class Chat(
    val id: String,
    val name: String,
    val messageCount: Int,
    val color: Int = Color.parseColor("#000000"),
)
