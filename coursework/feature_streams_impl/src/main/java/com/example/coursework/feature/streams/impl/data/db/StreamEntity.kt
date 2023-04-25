package com.example.coursework.feature.streams.impl.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streams")
data class StreamEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val subscribed: Boolean,
)
