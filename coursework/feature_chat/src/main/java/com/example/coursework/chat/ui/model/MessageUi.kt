package com.example.coursework.chat.ui.model

import com.example.feature_chat.R
import java.time.LocalDateTime

data class MessageUi(
    val id: String,
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val reactions: List<ReactionUi>,
    val posted: LocalDateTime,
) : ChatItem {
    override val viewType: Int = VIEW_TYPE
    override val uid: String = id

    companion object {
        val VIEW_TYPE =  R.layout.item_message
    }
}
