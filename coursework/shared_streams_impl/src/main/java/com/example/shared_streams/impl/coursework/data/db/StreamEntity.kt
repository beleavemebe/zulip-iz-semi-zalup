package com.example.shared_streams.impl.coursework.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streams")
data class StreamEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val subscribed: Boolean,
)
