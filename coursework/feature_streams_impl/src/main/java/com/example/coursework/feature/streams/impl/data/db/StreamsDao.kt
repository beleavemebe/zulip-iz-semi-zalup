package com.example.coursework.feature.streams.impl.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coursework.core.database.BaseDao

@Dao
interface StreamsDao : BaseDao {
    @Query("SELECT * FROM streams WHERE subscribed = :subscribed")
    suspend fun getStreams(subscribed: Boolean): List<StreamEntity>

    @Query("DELETE FROM streams WHERE subscribed = :subscribed")
    suspend fun clearStreams(subscribed: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeStreams(entities: List<StreamEntity>)

    @Query("SELECT * FROM topics WHERE streamId = :streamId")
    suspend fun getTopicsForStream(streamId: Int): List<TopicEntity>

    @Query("DELETE FROM topics")
    suspend fun clearTopics()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeTopics(entities: List<TopicEntity>)
}
