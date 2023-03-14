package com.example.coursework.chat.ui.recycler

import android.view.View
import com.example.coursework.chat.ui.model.ChatItem
import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.chat.ui.view.AddReactionView
import com.example.coursework.chat.ui.view.EmoteReactionView
import com.example.feature_chat.R
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class ChatViewHolderFactory(
    private val onReactionClicked: (messageUi: MessageUi, emoji: String) -> Unit,
    private val onAddReactionClicked: (messageUi: MessageUi) -> Unit,
) : CoroutinesHolderFactory() {
    private val reactionsClickListener = ReactionsClickListener()

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out ChatItem> {
        return when (viewType) {
            R.layout.item_message -> MessageUiViewHolder(view, longClicks, reactionsClickListener)
            R.layout.item_date_header -> DateHeaderUiViewHolder(view, clicks)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    inner class ReactionsClickListener {
        fun onClick(view: View, messageUi: MessageUi) {
            when (view) {
                is EmoteReactionView -> onReactionClicked(messageUi, view.reactionEmote)
                is AddReactionView -> onAddReactionClicked(messageUi)
            }
        }
    }
}
