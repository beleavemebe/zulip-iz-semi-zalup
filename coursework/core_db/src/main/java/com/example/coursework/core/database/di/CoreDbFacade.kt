package com.example.coursework.core.database.di

import com.example.coursework.core.database.DaoProvider
import com.example.coursework.core.database.DaoSupplier
import com.example.coursework.core.database.internal.MapLookupDaoProvider
import com.example.coursework.core.di.BaseApi
import com.example.coursework.core.di.BaseDeps
import com.example.coursework.core.di.DaggerComponent
import com.example.coursework.core.di.FeatureFacade

class CoreDbDeps(val daoSuppliers: List<DaoSupplier>) : BaseDeps

class CoreDbApi(val daoProvider: DaoProvider) : BaseApi

object CoreDbFacade : FeatureFacade<CoreDbDeps, CoreDbApi, DaggerComponent>() {
    override fun createComponent(deps: CoreDbDeps) = DaggerComponent

    override fun createApi(
        component: DaggerComponent,
        deps: CoreDbDeps,
    ): CoreDbApi {
        return CoreDbApi(
            daoProvider = MapLookupDaoProvider(deps.daoSuppliers.toMap())
        )
    }
}
