package com.example.coursework.topic.impl.data.api

import com.example.coursework.topic.impl.data.model.response.MessagesResponse
import retrofit2.http.*

interface MessagesApi {
    @GET("messages")
    suspend fun getMessages(
        @Query("anchor") anchor: String,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Query("narrow") narrow: String,
    ): MessagesResponse

    @POST("messages")
    suspend fun sendMessage(
        @Query("type") type: String = "stream",
        @Query("to") stream: Int,
        @Query("topic") topic: String,
        @Query("content") content: String,
    )

    @POST("messages/{message_id}/reactions")
    suspend fun addReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String
    )

    @DELETE("messages/{message_id}/reactions")
    suspend fun deleteReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String
    )
}
