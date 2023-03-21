package com.example.coursework.feature.channels.domain

import com.example.coursework.shared.chat.Chat

data class Channel(
    val id: String,
    val name: String,
    val chats: List<Chat>
)
