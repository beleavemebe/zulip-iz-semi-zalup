package com.example.shared_streams.impl.coursework.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey val name: String,
    val streamId: Int,
)
