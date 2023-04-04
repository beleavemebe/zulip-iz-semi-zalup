package com.example.coursework.feature.channels.ui.model

import com.example.coursework.feature.channels.R

data class TopicUi(
    val streamId: Int,
    val name: String,
    val messageCount: Int,
    val color: Int,
) : StreamsItem {
    override val uid: String = name
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_topic
    }
}
