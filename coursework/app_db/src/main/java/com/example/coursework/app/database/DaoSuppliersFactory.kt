package com.example.coursework.app.database

import com.example.shared_streams.impl.coursework.data.db.StreamsDao
import javax.inject.Inject

class DaoSuppliersFactory @Inject constructor(
    private val appDatabase: AppDatabase
) {
    fun getDaoSuppliers() = listOf(
        StreamsDao::class to { appDatabase.streamsDao() },
        com.example.coursework.shared_messages.impl.data.db.TopicDao::class to { appDatabase.topicDao() }
    )
}
