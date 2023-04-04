package com.example.coursework.feature.channels.ui.recycler

import android.view.View
import com.example.coursework.feature.channels.R
import com.example.coursework.feature.channels.ui.model.StreamsItem
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class StreamsViewHolderFactory : CoroutinesHolderFactory() {
    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out StreamsItem> {
        return when (viewType) {
            R.layout.item_channel -> StreamUiViewHolder(view, clicks)
            R.layout.item_topic -> TopicUiViewHolder(view, clicks)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }
}
