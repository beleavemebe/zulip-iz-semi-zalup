package com.example.coursework.core.network.internal

import java.io.IOException

data class BackendException(
    val httpCode: Int? = null,
    val body: String? = null,
    override val message: String? = null,
    override val cause: Throwable? = null
) : IOException(message)
