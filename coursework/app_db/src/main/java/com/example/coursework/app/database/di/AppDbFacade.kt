package com.example.coursework.app.database.di

import android.content.Context
import com.example.coursework.core.database.DaoSupplier
import com.example.coursework.core.di.BaseApi
import com.example.coursework.core.di.BaseDeps
import com.example.coursework.core.di.FeatureFacade

interface AppDbDeps : BaseDeps {
    val context: Context
}

interface AppDbApi : BaseApi {
    val daoSuppliers: Set<DaoSupplier>
}

object AppDbFacade : FeatureFacade<AppDbDeps, AppDbApi, AppDbComponent>() {
    override fun createComponent(deps: AppDbDeps): AppDbComponent {
        return DaggerAppDbComponent.factory().create(deps)
    }

    override fun createApi(component: AppDbComponent, deps: AppDbDeps): AppDbApi {
        return component
    }
}
