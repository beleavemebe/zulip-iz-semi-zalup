package com.example.coursework.app.database

import com.example.coursework.feature.streams.impl.data.db.StreamsDao
import com.example.coursework.topic.impl.data.db.TopicDao
import javax.inject.Inject

class DaoSuppliersFactory @Inject constructor(
    private val appDatabase: AppDatabase
) {
    fun getDaoSuppliers() = listOf(
        StreamsDao::class to { appDatabase.streamsDao() },
        TopicDao::class to { appDatabase.topicDao() }
    )
}
