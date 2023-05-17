package com.example.coursework.feature.streams.impl.ui.model

import com.example.coursework.feature.streams.R

object CreateTopicUi : StreamsItem {
    val VIEW_TYPE = R.layout.item_create_topic

    override val uid: String = javaClass.simpleName
    override val viewType = VIEW_TYPE
}
