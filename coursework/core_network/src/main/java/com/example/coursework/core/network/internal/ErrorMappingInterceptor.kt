package com.example.coursework.core.network.internal

import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class ErrorMappingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val code = response.code
        if (code == HttpURLConnection.HTTP_OK) {
            return response
        } else {
            val message = response.message
            response.close()
            throw BackendException(
                httpCode = code,
                message = message
            )
        }
    }
}
