package com.example.coursework.feature.streams.impl.data.api

import com.example.coursework.feature.streams.impl.data.model.response.AllStreamsResponse
import com.example.coursework.feature.streams.impl.data.model.response.SubscribedStreamsResponse
import com.example.coursework.feature.streams.impl.data.model.response.TopicsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StreamsApi {
    @GET("streams")
    suspend fun getAllStreams(): AllStreamsResponse

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreamsResponse

    @GET("users/me/{stream_id}/topics")
    suspend fun getStreamTopics(
        @Path("stream_id") streamId: Int
    ): TopicsResponse
}
