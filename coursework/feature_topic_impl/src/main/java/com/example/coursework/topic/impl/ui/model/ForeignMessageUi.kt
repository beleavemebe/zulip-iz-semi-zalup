package com.example.coursework.topic.impl.ui.model

import com.example.feature.topic.impl.R
import java.time.LocalDateTime

data class ForeignMessageUi(
    override val id: Int,
    override val message: String,
    override val reactions: List<ReactionUi>,
    override val posted: LocalDateTime,
    val author: String,
    val authorImageUrl: String,
) : MessageUi {
    override val viewType: Int = VIEW_TYPE

    override fun updateReactions(reactions: List<ReactionUi>) = copy(reactions = reactions)

    companion object {
        val VIEW_TYPE = R.layout.item_message
    }
}
