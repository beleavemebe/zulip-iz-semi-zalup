package com.example.coursework.feature.streams.impl.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.feature.streams.impl.data.StreamsRepositoryImpl
import com.example.coursework.feature.streams.impl.data.api.StreamsApi
import com.example.coursework.feature.streams.impl.data.db.StreamsDao
import com.example.coursework.feature.streams.impl.domain.repository.StreamsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [StreamsModule.Bindings::class])
object StreamsModule {
    @Provides
    fun provideStreamsApi(
        retrofit: Retrofit
    ): StreamsApi = retrofit.create()

    @Provides
    fun provideStreamsDao(
        daoProvider: DaoProvider
    ): StreamsDao = daoProvider[StreamsDao::class]

    @Module
    interface Bindings {
        @Binds
        fun bindStreamsRepository(impl: StreamsRepositoryImpl): StreamsRepository
    }
}
