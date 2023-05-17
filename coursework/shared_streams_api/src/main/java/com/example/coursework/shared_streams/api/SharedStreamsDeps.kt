package com.example.coursework.shared_streams.api

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.di.BaseDeps
import retrofit2.Retrofit

interface SharedStreamsDeps : BaseDeps {
    val retrofit: Retrofit
    val daoProvider: DaoProvider
}
