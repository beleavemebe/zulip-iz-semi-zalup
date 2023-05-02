package com.example.coursework.shared.profile.impl.di

import com.example.coursework.shared.profile.impl.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.impl.data.api.UsersApi
import com.example.shared.profile.api.domain.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [SharedProfileModule.Bindings::class])
object SharedProfileModule {
    @Provides
    fun provideUsersApi(
        retrofit: Retrofit
    ): UsersApi = retrofit.create()

    @Module
    interface Bindings {
        @Binds
        fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository
    }
}
