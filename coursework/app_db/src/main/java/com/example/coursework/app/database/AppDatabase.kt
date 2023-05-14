package com.example.coursework.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coursework.feature.streams.impl.data.db.StreamEntity
import com.example.coursework.feature.streams.impl.data.db.StreamsDao
import com.example.coursework.feature.streams.impl.data.db.TopicEntity
import com.example.coursework.topic.impl.data.db.MessageEntity
import com.example.coursework.topic.impl.data.db.ReactionEntity
import com.example.coursework.topic.impl.data.db.TopicDao

@Database(
    version = 1,
    entities = [StreamEntity::class, TopicEntity::class, MessageEntity::class, ReactionEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun streamsDao(): StreamsDao
    abstract fun topicDao(): TopicDao

    companion object {
        private const val DB_NAME = "app.db"

        fun newInstance(context: Context): AppDatabase =
            Room.databaseBuilder(
                context, AppDatabase::class.java, DB_NAME
            ).build()
    }
}