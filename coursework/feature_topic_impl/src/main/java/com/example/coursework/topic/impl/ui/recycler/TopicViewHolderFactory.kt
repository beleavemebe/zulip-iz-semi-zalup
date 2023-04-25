package com.example.coursework.topic.impl.ui.recycler

import android.view.View
import com.example.coursework.topic.impl.ui.model.DateHeaderUi
import com.example.coursework.topic.impl.ui.model.LoadingUi
import com.example.coursework.topic.impl.ui.model.MessageUi
import com.example.coursework.topic.impl.ui.model.TopicItem
import com.example.coursework.topic.impl.ui.view.AddReactionView
import com.example.coursework.topic.impl.ui.view.EmoteReactionView
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class TopicViewHolderFactory(
    private val onReactionClicked: (messageUi: MessageUi, emoji: String) -> Unit,
    private val onAddReactionClicked: (messageUi: MessageUi) -> Unit,
) : CoroutinesHolderFactory() {
    private val reactionsClickListener = ReactionsClickListener()

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out TopicItem> {
        return when (viewType) {
            MessageUi.VIEW_TYPE -> MessageUiViewHolder(view, longClicks, reactionsClickListener)
            DateHeaderUi.VIEW_TYPE -> DateHeaderUiViewHolder(view)
            LoadingUi.VIEW_TYPE -> LoadingUiViewHolder(view)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    inner class ReactionsClickListener {
        fun onClick(view: View, messageUi: MessageUi) {
            when (view) {
                is EmoteReactionView -> onReactionClicked(messageUi, view.emoteName)
                is AddReactionView -> onAddReactionClicked(messageUi)
            }
        }
    }
}
