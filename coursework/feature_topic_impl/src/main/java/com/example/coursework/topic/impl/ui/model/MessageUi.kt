package com.example.coursework.topic.impl.ui.model

import com.example.feature.topic.impl.R
import java.time.LocalDateTime

interface MessageUi : TopicItem {
    val id: Int
    val message: String
    val reactions: List<ReactionUi>
    val posted: LocalDateTime

    override val uid: String
        get() = id.toString()

    fun updateReactions(reactions: List<ReactionUi>): MessageUi

    companion object {
        val VIEW_TYPE =  R.layout.item_message

        fun calcPayload(oldItem: TopicItem, newItem: TopicItem): Any? {
            if (oldItem !is MessageUi || newItem !is MessageUi) return null
            if (oldItem.reactions != newItem.reactions) return newItem.reactions
            return null
        }
    }
}
