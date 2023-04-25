package com.example.coursework.app.database.di

import android.content.Context
import com.example.coursework.app.database.AppDatabase
import com.example.coursework.app.database.DaoSuppliersFactory
import com.example.coursework.core.database.DaoSupplier
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

@Module
object AppDbModule {
    @Provides
    fun provideAppDatabase(
        context: Context
    ): AppDatabase = AppDatabase.newInstance(context)

    @[Provides ElementsIntoSet]
    fun bindDaoSuppliers(
        daoSuppliersFactory: DaoSuppliersFactory
    ): Set<DaoSupplier> {
        return daoSuppliersFactory.getDaoSuppliers().toSet()
    }
}
