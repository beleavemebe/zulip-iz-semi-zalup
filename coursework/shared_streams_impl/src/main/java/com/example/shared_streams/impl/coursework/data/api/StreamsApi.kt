package com.example.shared_streams.impl.coursework.data.api

import com.example.shared_streams.impl.coursework.data.model.response.AllStreamsResponse
import com.example.shared_streams.impl.coursework.data.model.response.SubscribedStreamsResponse
import com.example.shared_streams.impl.coursework.data.model.response.TopicsResponse
import retrofit2.http.*

interface StreamsApi {
    @GET("streams")
    suspend fun getAllStreams(): AllStreamsResponse

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreamsResponse

    @GET("users/me/{stream_id}/topics")
    suspend fun getStreamTopics(
        @Path("stream_id") streamId: Int
    ): TopicsResponse

    @FormUrlEncoded
    @POST("users/me/subscriptions")
    suspend fun createStream(
        @Field("subscriptions") streamJson: String
    )
}
