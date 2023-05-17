package com.example.coursework.feature.streams.impl.ui.recycler

import android.view.View
import com.example.coursework.feature.streams.impl.ui.model.*
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class StreamsViewHolderFactory : CoroutinesHolderFactory() {
    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out StreamsItem> {
        return when (viewType) {
            StreamUi.VIEW_TYPE -> StreamUiViewHolder(view, clicks)
            TopicUi.VIEW_TYPE -> TopicUiViewHolder(view, clicks)
            TopicShimmerUi.VIEW_TYPE -> TopicShimmerUiViewHolder(view)
            CreateTopicUi.VIEW_TYPE -> CreateTopicUiViewHolder(view, clicks)
            ViewAllMessagesUi.VIEW_TYPE -> ViewAllMessagesUiViewHolder(view, clicks)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }
}
