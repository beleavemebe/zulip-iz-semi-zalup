package com.example.coursework.app.database

import com.example.coursework.topic.impl.data.db.TopicDao
import com.example.shared_streams.impl.coursework.data.db.StreamsDao
import javax.inject.Inject

class DaoSuppliersFactory @Inject constructor(
    private val appDatabase: AppDatabase
) {
    fun getDaoSuppliers() = listOf(
        StreamsDao::class to { appDatabase.streamsDao() },
        TopicDao::class to { appDatabase.topicDao() }
    )
}
