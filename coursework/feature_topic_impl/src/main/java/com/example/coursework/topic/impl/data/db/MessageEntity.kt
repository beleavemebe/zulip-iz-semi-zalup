package com.example.coursework.topic.impl.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val topic: String,
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val posted: Long,
)
