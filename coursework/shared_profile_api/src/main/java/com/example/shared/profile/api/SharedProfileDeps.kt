package com.example.shared.profile.api

import com.example.coursework.core.di.BaseDeps
import retrofit2.Retrofit

interface SharedProfileDeps : BaseDeps {
    val retrofit: Retrofit
}
