package com.example.coursework.feature.channels.domain

import com.example.core.ui.throwRandomly
import com.example.coursework.shared.chat.Chat
import kotlinx.coroutines.delay

class GetAllStreams {
    suspend fun execute(): List<Channel> {
        delay(100)
        throwRandomly()
        return listOf(
            Channel("0", "#general", chats = listOf(
                Chat("0", "Testing", 1240, 0xFF2A9D8F.toInt()),
                Chat("1", "Bruh", 24, 0xFFE9C46A.toInt()),
            )),
            Channel("1", "#dev", listOf(
                Chat("4", "Sample text", 0, 0xFF5532EB.toInt())
            )),
            Channel("2", "#design", listOf(
                Chat("5", "Sample text", 0, 0xFF5532EB.toInt())
            )),
            Channel("3", "#PR", listOf(
                Chat("6", "Sample text", 0, 0xFF5532EB.toInt())
            )),
        )
    }
}
