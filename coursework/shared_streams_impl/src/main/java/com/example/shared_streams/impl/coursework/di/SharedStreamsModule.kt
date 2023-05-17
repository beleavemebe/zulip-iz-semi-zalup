package com.example.shared_streams.impl.coursework.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.example.shared_streams.impl.coursework.data.StreamsRepositoryImpl
import com.example.shared_streams.impl.coursework.data.api.StreamsApi
import com.example.shared_streams.impl.coursework.data.db.StreamsDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [SharedStreamsModule.Bindings::class])
object SharedStreamsModule {
    @SharedStreamsScope
    @Provides
    fun provideStreamsApi(
        retrofit: Retrofit
    ): StreamsApi = retrofit.create()

    @SharedStreamsScope
    @Provides
    fun provideStreamsDao(
        daoProvider: DaoProvider
    ): StreamsDao = daoProvider[StreamsDao::class]

    @Module
    interface Bindings {
        @SharedStreamsScope
        @Binds
        fun bindStreamsRepository(impl: StreamsRepositoryImpl): StreamsRepository
    }
}
