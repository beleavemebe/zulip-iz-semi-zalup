package com.example.coursework.feature.channels.data.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                Credentials.basic(
                    "romanshemanovskii@gmail.com",
                    "K50Al59Y7pf0gU6cBGgQcMTwalUBLQVM"
                )
            )
            .build()

        return chain.proceed(request)
    }
}
