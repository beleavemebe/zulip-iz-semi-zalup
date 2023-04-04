package com.example.coursework.core.network

import android.util.Log
import androidx.viewbinding.BuildConfig
import com.example.coursework.core.network.internal.AuthorizationInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object NetworkModule {
    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = BuildConfig.DEBUG
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor())
        .addInterceptor(
            HttpLoggingInterceptor { Log.d("http-debug", it) }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://tinkoff-android-spring-2023.zulipchat.com/api/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}
