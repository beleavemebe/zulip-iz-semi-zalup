package com.example.coursework.shared.profile.data.api

import com.example.coursework.shared.profile.data.model.*
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {
    @GET("users/me")
    suspend fun getOwnUser(): UserDto

    @GET("users")
    suspend fun getUsers(): UsersResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): UserResponse

    @GET("users/{id}/presence")
    suspend fun getUserPresence(
        @Path("id") id: Int
    ): PresenceResponse

    @GET("realm/presence")
    suspend fun getPresenceOfAllUsers(): AllUsersPresenceResponse

    companion object {
        fun create(retrofit: Retrofit) = retrofit.create<UsersApi>()
    }
}
