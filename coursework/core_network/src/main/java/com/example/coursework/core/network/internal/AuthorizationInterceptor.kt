package com.example.coursework.core.network.internal

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationInterceptor : Interceptor {
    private val credentials = Credentials.basic(
        "romanshemanovskii@gmail.com",
        "K50Al59Y7pf0gU6cBGgQcMTwalUBLQVM"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", credentials)
            .build()

        return chain.proceed(request)
    }
}
