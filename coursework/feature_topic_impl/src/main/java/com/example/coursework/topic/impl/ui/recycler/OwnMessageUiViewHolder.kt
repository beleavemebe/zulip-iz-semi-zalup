package com.example.coursework.topic.impl.ui.recycler

import android.view.View
import com.example.coursework.topic.impl.ui.model.OwnMessageUi
import com.example.feature.topic.impl.databinding.LayoutOwnMessageBinding
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class OwnMessageUiViewHolder(
    view: View,
    longClicks: TiRecyclerClickListener,
    reactionClickListener: TopicViewHolderFactory.ReactionsClickListener
) : BaseMessageUiViewHolder<OwnMessageUi>(view, longClicks, reactionClickListener) {
    private val binding = LayoutOwnMessageBinding.bind(view)

    override val root = binding.root
    override val tvMessageContent = binding.tvMessageContent
    override val fbReactions = binding.fbReactions
}
