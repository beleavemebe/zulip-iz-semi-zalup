package com.example.coursework.core.database.internal

import com.example.coursework.core.database.BaseDao
import com.example.coursework.core.database.DaoProvider
import kotlin.reflect.KClass

internal class MapLookupDaoProvider(
    private val daoSupplierMap: Map<KClass<out BaseDao>, () -> BaseDao>
) : DaoProvider {
    @Suppress("UNCHECKED_CAST")
    override fun <Dao : BaseDao> get(daoClass: KClass<Dao>): Dao {
        return requireNotNull(daoSupplierMap[daoClass]).invoke() as Dao
    }
}
