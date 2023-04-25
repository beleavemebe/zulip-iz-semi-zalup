package com.example.coursework.topic.impl.ui.model

import com.example.feature.topic.impl.R
import java.time.LocalDateTime

data class MessageUi(
    val id: Int,
    val author: String,
    val authorImageUrl: String,
    val message: String,
    val reactions: List<ReactionUi>,
    val posted: LocalDateTime,
) : TopicItem {
    override val viewType: Int = VIEW_TYPE
    override val uid: String = id.toString()

    companion object {
        val VIEW_TYPE =  R.layout.item_message

        fun calcPayload(oldItem: TopicItem, newItem: TopicItem): Any? {
            if (oldItem !is MessageUi || newItem !is MessageUi) return null
            if (oldItem.reactions != newItem.reactions) return newItem.reactions
            return null
        }
    }
}
