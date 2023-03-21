package com.example.coursework.feature.channels.domain

import com.example.coursework.shared.chat.Chat

class GetSubscribedChannels {
    fun execute() = listOf(
        Channel("0", "#general", chats = listOf(
            Chat("0", "Testing", 1240, 0xFF2A9D8F.toInt()),
            Chat("1", "Bruh", 24, 0xFFE9C46A.toInt()),
        )),
        Channel("1", "#dev", listOf(
            Chat("3", "Sample text", 0, 0xFF5532EB.toInt())
        )),
    )
}
