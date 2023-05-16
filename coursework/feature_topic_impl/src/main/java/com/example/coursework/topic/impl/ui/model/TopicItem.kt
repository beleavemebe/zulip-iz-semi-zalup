package com.example.coursework.topic.impl.ui.model

import androidx.collection.SparseArrayCompat
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.PayloadMapper

interface TopicItem : ViewTyped {
    companion object {
        val payloadMappers by lazy {
            SparseArrayCompat<PayloadMapper<TopicItem>>(2).apply {
                put(MessageUi.VIEW_TYPE, MessageUi::calcPayload)
                put(OwnMessageUi.VIEW_TYPE, MessageUi::calcPayload)
            }
        }
    }
}
