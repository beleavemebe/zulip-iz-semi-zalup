package com.example.coursework.core.network.di

import com.example.coursework.core.di.BaseApi
import com.example.coursework.core.di.BaseDeps
import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.core.di.FeatureFacade
import com.example.coursework.core.network.internal.NetworkModule
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL = "https://tinkoff-android-spring-2023.zulipchat.com/api/v1/"

class CoreNetworkDeps(
    val baseUrl: String = BASE_URL
) : BaseDeps

interface CoreNetworkApi : BaseApi {
    val json: Json
    val okHttpClient: OkHttpClient
    val retrofit: Retrofit
}

object CoreNetworkFacade : FeatureFacade<CoreNetworkDeps, CoreNetworkApi, DaggerComponent>() {
    override fun createComponent(deps: CoreNetworkDeps) = DaggerComponent

    override fun createApi(component: DaggerComponent, deps: CoreNetworkDeps): CoreNetworkApi {
        return object : CoreNetworkApi {
            override val json = NetworkModule.createJson()
            override val okHttpClient = NetworkModule.createOkHttpClient()
            override val retrofit = NetworkModule.createRetrofit(deps.baseUrl, okHttpClient, json)
        }
    }
}
