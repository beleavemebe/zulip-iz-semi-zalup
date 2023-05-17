package com.example.coursework.shared_messages.impl.data.db

import androidx.room.*
import com.example.coursework.core.database.BaseDao

@Dao
interface TopicDao : BaseDao {
    @Transaction
    @Query("SELECT * FROM messages WHERE topic = :topicName ORDER BY posted DESC")
    suspend fun getCachedMessages(topicName: String): List<MessageWithReactions>

    @Query("DELETE FROM messages WHERE topic = :topicName")
    suspend fun clearCacheForTopic(topicName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeMessages(messages: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeReactions(reactions: List<ReactionEntity>)
}
