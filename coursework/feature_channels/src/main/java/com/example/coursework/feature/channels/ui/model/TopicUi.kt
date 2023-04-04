package com.example.coursework.feature.channels.ui.model

import com.example.coursework.feature.channels.R

data class ChatUi(
    val id: String,
    val name: String,
    val messageCount: Int,
    val color: Int,
) : ChannelsItem {
    override val uid: String = id
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_chat
    }
}
