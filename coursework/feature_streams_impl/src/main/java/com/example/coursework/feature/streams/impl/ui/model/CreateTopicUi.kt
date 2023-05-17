package com.example.coursework.feature.streams.impl.ui.model

import com.example.coursework.feature.streams.R

class CreateTopicUi(val streamUi: StreamUi) : StreamsItem {
    override val uid: String = javaClass.simpleName
    override val viewType = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_create_topic
    }
}
