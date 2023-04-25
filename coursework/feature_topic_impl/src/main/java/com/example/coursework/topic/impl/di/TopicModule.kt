package com.example.coursework.topic.impl.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.topic.impl.data.MessageRepositoryImpl
import com.example.coursework.topic.impl.data.api.MessagesApi
import com.example.coursework.topic.impl.data.db.TopicDao
import com.example.coursework.topic.impl.domain.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [TopicModule.Bindings::class])
object TopicModule {
    @Provides
    fun provideMessagesApi(
        retrofit: Retrofit
    ): MessagesApi = retrofit.create()

    @Provides
    fun provideTopicDao(
        daoProvider: DaoProvider
    ): TopicDao = daoProvider[TopicDao::class]

    @Module
    interface Bindings {
        @Binds
        fun bindMessagesRepository(impl: MessageRepositoryImpl): MessageRepository
    }
}
