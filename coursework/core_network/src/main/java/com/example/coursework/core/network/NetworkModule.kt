package com.example.coursework.core.network

import androidx.viewbinding.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitFactory {
    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = BuildConfig.DEBUG
    }

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit = Retrofit.Builder()
        .client(OkHttpClientFactory.okHttpClient)
        .baseUrl("https://tinkoff-android-spring-2023.zulipchat.com/api/v1/")
        .addConverterFactory(JsonFactory.json.asConverterFactory("application/json".toMediaType()))
        .build()
}
