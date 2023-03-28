package com.example.coursework.core.utils

interface CacheContainer {

    fun isValueInCache(key: String): Boolean

    fun <T> putIntoCache(key: String, result: T)

    fun <T> retrieveFromCache(key: String): T

    class Map : CacheContainer {
        private val cache = mutableMapOf<String, Any>()

        override fun isValueInCache(key: String) = cache[key] != null

        @Suppress("UNCHECKED_CAST")
        override fun <T> putIntoCache(key: String, result: T) {
            (cache as MutableMap<String, T>)[key] = result
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T> retrieveFromCache(key: String) = cache[key] as T
    }
}

inline fun <R> CacheContainer.cache(
    key: String,
    forceUpdate: Boolean = false,
    calculation: () -> R,
): R {
    if (!forceUpdate && isValueInCache(key)) return retrieveFromCache(key)
    return calculation().also { result ->
        putIntoCache(key, result)
    }
}
