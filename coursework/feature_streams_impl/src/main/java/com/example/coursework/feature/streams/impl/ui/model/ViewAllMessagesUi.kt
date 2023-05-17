package com.example.coursework.feature.streams.impl.ui.model

import com.example.coursework.feature.streams.R

object ViewAllMessagesUi : StreamsItem {
    val VIEW_TYPE = R.layout.item_view_all_messages

    override val uid: String = javaClass.simpleName
    override val viewType = VIEW_TYPE
}
