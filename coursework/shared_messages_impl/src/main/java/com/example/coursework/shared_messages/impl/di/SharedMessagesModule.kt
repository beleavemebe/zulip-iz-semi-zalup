package com.example.coursework.shared_messages.impl.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.shared_messages.api.domain.MessageRepository
import com.example.coursework.shared_messages.impl.data.MessageRepositoryImpl
import com.example.coursework.shared_messages.impl.data.api.MessagesApi
import com.example.coursework.shared_messages.impl.data.db.TopicDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [SharedMessagesModule.Bindings::class])
object SharedMessagesModule {
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
