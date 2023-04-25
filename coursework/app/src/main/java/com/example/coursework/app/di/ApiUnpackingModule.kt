package com.example.coursework.app.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.database.di.CoreDbApi
import com.example.shared.profile.api.SharedProfileApi
import com.example.shared.profile.api.domain.UsersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiUnpackingModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        api: SharedProfileApi
    ): UsersRepository {
        return api.usersRepository
    }

    @Provides
    @Singleton
    fun provideDaoProvider(
        api: CoreDbApi
    ): DaoProvider {
        return api.daoProvider
    }
}
