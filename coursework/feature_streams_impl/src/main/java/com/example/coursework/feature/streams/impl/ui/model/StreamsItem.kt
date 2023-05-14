package com.example.coursework.feature.streams.impl.ui.model

import androidx.collection.SparseArrayCompat
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.PayloadMapper

interface StreamsItem : ViewTyped {
    companion object {
        val payloadMappers by lazy {
            SparseArrayCompat<PayloadMapper<StreamsItem>>(2).apply {
                put(StreamUi.VIEW_TYPE, StreamUi::calcPayload)
            }
        }
    }
}
