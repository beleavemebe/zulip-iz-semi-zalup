package com.example.coursework.feature.streams.impl.ui.model

import com.example.coursework.feature.streams.R

data class StreamUi(
    val id: Int,
    val name: String,
    val isExpanded: Boolean,
) : StreamsItem {
    override val uid: String = id.toString()
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_stream

        fun calcPayload(oldItem: StreamsItem, newItem: StreamsItem): Any? {
            if (oldItem !is StreamUi || newItem !is StreamUi) return null
            if (oldItem.isExpanded != newItem.isExpanded) return newItem.isExpanded
            return null
        }
    }
}
