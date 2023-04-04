package com.example.coursework.feature.channels.ui.model

import com.example.coursework.feature.channels.R

data class ChannelUi(
    val tag: String,
    var isExpanded: Boolean,
    val chatUis: List<ChatUi>
) : ChannelsItem {
    override val uid: String = tag
    override val viewType: Int = VIEW_TYPE

    companion object {
        val VIEW_TYPE = R.layout.item_channel
    }
}
