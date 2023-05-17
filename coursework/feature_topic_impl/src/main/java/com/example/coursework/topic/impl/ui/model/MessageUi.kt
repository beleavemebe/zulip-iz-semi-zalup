package com.example.coursework.topic.impl.ui.model

import java.time.LocalDateTime

interface MessageUi : TopicItem {
    val id: Int
    val message: String
    val reactions: List<ReactionUi>
    val posted: LocalDateTime

    override val uid: String
        get() = id.toString()

    fun updateReactions(reactions: List<ReactionUi>): MessageUi

    fun updateContent(content: String): MessageUi

    companion object {
        fun calcPayload(oldItem: TopicItem, newItem: TopicItem): Any? {
            if (oldItem !is MessageUi || newItem !is MessageUi) return null
            if (oldItem.reactions != newItem.reactions) return newItem.reactions
            return null
        }
    }
}
