package com.example.coursework.shared_messages.impl.data.api

import com.example.coursework.shared_messages.impl.data.model.response.MessagesResponse
import retrofit2.http.*

interface MessagesApi {
    @GET("messages")
    suspend fun getMessages(
        @Query("anchor") anchor: Any,
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

    @DELETE("messages/{message_id}")
    suspend fun deleteMessage(
        @Path("message_id") messageId: Int,
    )

    @PATCH("messages/{message_id}")
    suspend fun editMessage(
        @Path("message_id") messageId: Int,
        @Query("content") content: String
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
