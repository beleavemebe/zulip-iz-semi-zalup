package com.example.coursework.feature.channels.data.api

import com.example.coursework.feature.channels.data.model.response.AllStreamsResponse
import com.example.coursework.feature.channels.data.model.response.SubscribedStreamsResponse
import com.example.coursework.feature.channels.data.model.response.TopicsResponse
import retrofit2.Retrofit
import retrofit2.create
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

    companion object {
        fun create(retrofit: Retrofit) = retrofit.create<StreamsApi>()
    }
}
