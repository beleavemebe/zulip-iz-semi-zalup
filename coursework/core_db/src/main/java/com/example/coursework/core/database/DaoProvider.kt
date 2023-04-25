package com.example.coursework.core.database

import kotlin.reflect.KClass

interface BaseDao

typealias DaoSupplier = Pair<KClass<out BaseDao>, () -> BaseDao>

interface DaoProvider {
    operator fun <Dao : BaseDao> get(daoClass: KClass<Dao>): Dao
}
