package com.example.coursework.shared_messages.impl.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "reactions",
    foreignKeys = [
        ForeignKey(
            entity = MessageEntity::class,
            parentColumns = ["id"],
            childColumns = ["messageId"],
            onDelete = CASCADE
        )
    ]
)
data class ReactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageId: Int,
    val emojiCode: String,
    val emojiName: String,
    val userId: Int,
)
