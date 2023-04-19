package com.example.coursework.topic.impl.ui.recycler

import android.text.Html
import android.view.View
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.example.coursework.topic.impl.ui.model.MessageUi
import com.example.coursework.topic.impl.ui.view.MessageView
import com.example.feature.topic.impl.databinding.LayoutMessageBinding
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class MessageUiViewHolder(
    view: View,
    longClicks: TiRecyclerClickListener,
    private val reactionClickListener: TopicViewHolderFactory.ReactionsClickListener
) : BaseViewHolder<MessageUi>(view, longClicks) {
    private val binding = LayoutMessageBinding.bind(view)

    override fun bind(item: MessageUi) {
        binding.tvMessageAuthor.text = item.author
        binding.tvMessageContent.text = Html.fromHtml(item.message, Html.FROM_HTML_MODE_COMPACT)

        val root = binding.root as MessageView
        root.messageReactions = item.reactions
        binding.fbReactions.children.toList().forEach { view ->
            view.setOnClickListener {
                reactionClickListener.onClick(view, item)
            }
        }

        Glide.with(root)
            .load(item.authorImageUrl)
            .into(binding.ivMessageAuthorPic)
    }
}
