package com.example.coursework.topic.impl.ui.model

import com.example.feature.topic.impl.R

object LoadingUi : TopicItem {
    val VIEW_TYPE = R.layout.item_loading

    override val viewType: Int = VIEW_TYPE
    override val uid: String = "LoadingUi"
}
