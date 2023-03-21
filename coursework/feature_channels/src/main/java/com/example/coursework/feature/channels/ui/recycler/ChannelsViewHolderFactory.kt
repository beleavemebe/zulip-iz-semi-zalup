package com.example.coursework.feature.channels.ui.recycler

import android.view.View
import com.example.coursework.feature.channels.R
import com.example.coursework.feature.channels.ui.model.ChannelsItem
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class ChannelsViewHolderFactory : CoroutinesHolderFactory() {
    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out ChannelsItem> {
        return when (viewType) {
            R.layout.item_channel -> ChannelUiViewHolder(view, clicks)
            R.layout.item_chat -> ChatUiViewHolder(view, clicks)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }
}
