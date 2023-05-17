package com.example.coursework.shared_messages.impl.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithReactions(
    @Embedded
    val message: MessageEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "messageId"
    )
    val reactions: List<ReactionEntity>
)
