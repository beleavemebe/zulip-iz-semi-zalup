package com.example.coursework.feature.streams.impl.ui.model

import com.example.coursework.feature.streams.R

data class TopicUi(
    val streamId: Int,
    val stream: String,
    val name: String,
    val color: Int,
) : StreamsItem {
    override val uid: String = name
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_topic
    }
}
