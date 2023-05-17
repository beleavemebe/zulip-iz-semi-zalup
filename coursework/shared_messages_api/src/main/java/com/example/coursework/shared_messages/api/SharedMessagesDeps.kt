package com.example.coursework.shared_messages.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import retrofit2.Retrofit

class SharedMessagesDeps(
    val retrofit: Retrofit,
    val daoProvider: DaoProvider
) : BaseDeps
