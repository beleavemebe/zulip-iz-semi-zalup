package com.example.coursework.topic.impl.data

import com.example.coursework.topic.impl.data.db.MessageEntity
import com.example.coursework.topic.impl.data.db.MessageWithReactions
import com.example.coursework.topic.impl.data.db.ReactionEntity
import com.example.coursework.topic.impl.data.model.dto.MessageDto
import com.example.coursework.topic.impl.data.model.dto.ReactionDto
import com.example.coursework.topic.impl.domain.model.Message
import com.example.coursework.topic.impl.domain.model.Reaction
import java.time.LocalDateTime
import java.time.ZoneOffset

object MessagesMapper {
    fun toMessage(topic: String, dto: MessageDto): Message {
        return Message(
            id = dto.id,
            topic = topic,
            author = dto.sender_full_name,
            authorImageUrl = dto.avatar_url,
            message = dto.content,
            posted = LocalDateTime.ofEpochSecond(dto.timestamp.toLong(), 0, ZoneOffset.MIN),
            reactions = dto.reactions.map(::toReaction)
        )
    }

    private fun toReaction(dto: ReactionDto): Reaction {
        return Reaction(
            emojiCode = dto.emoji_code,
            emojiName = dto.emoji_name,
            userId = dto.user_id
        )
    }

    fun toEntity(topic: String, message: Message): MessageEntity {
        return MessageEntity(
            id = message.id,
            topic = topic,
            author = message.author,
            authorImageUrl = message.authorImageUrl,
            message = message.message,
            posted = message.posted.toEpochSecond(ZoneOffset.MIN)
        )
    }

    fun toEntity(messageId: Int, reaction: Reaction): ReactionEntity {
        return ReactionEntity(
            messageId = messageId,
            emojiCode = reaction.emojiCode,
            emojiName = reaction.emojiName,
            userId = reaction.userId
        )
    }

    fun toMessage(view: MessageWithReactions): Message {
        return Message(
            id = view.message.id,
            topic = view.message.topic,
            author = view.message.author,
            authorImageUrl = view.message.authorImageUrl,
            message = view.message.message,
            posted = LocalDateTime.ofEpochSecond(view.message.posted, 0, ZoneOffset.MIN),
            reactions = view.reactions.map(::toReaction)
        )
    }

    private fun toReaction(entity: ReactionEntity): Reaction {
        return Reaction(
            emojiCode = entity.emojiCode,
            emojiName = entity.emojiName,
            userId = entity.userId
        )
    }
}
