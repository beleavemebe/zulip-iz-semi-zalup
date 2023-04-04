package com.example.coursework.chat.data

import com.example.coursework.chat.data.model.dto.MessageDto
import com.example.coursework.chat.data.model.dto.ReactionDto
import com.example.coursework.chat.model.Message
import com.example.coursework.chat.model.Reaction
import java.time.LocalDateTime
import java.time.ZoneOffset

object MessagesMapper {
    fun toMessage(dto: MessageDto): Message {
        return Message(
            id = dto.id,
            author = dto.sender_full_name,
            authorImageUrl = dto.avatar_url,
            message = dto.content,
            posted = LocalDateTime.ofEpochSecond(dto.timestamp.toLong(), 0, ZoneOffset.MIN),
            reactions = dto.reactions.map { toReaction(it) }
        )
    }

    fun toReaction(dto: ReactionDto): Reaction {
        return Reaction(
            emojiCode = dto.emoji_code,
            emojiName = dto.emoji_name,
            userId = dto.user_id
        )
    }
}
