package com.example.coursework.feature.channels.ui.model

import com.example.coursework.feature.channels.R

data class StreamUi(
    val id: Int,
    val tag: String,
    var isExpanded: Boolean,
) : StreamsItem {
    override val uid: String = id.toString()
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_channel
    }
}
