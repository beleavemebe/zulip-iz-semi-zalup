package com.example.coursework.topic.impl.ui.recycler

import android.view.View
import com.bumptech.glide.Glide
import com.example.coursework.topic.impl.ui.model.ForeignMessageUi
import com.example.feature.topic.impl.databinding.LayoutMessageBinding
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener


class MessageUiViewHolder(
    view: View,
    longClicks: TiRecyclerClickListener,
    reactionClickListener: TopicViewHolderFactory.ReactionsClickListener
) : BaseMessageUiViewHolder<ForeignMessageUi>(view, longClicks, reactionClickListener) {
    private val binding = LayoutMessageBinding.bind(view)

    override val root = binding.root
    override val tvMessageContent = binding.tvMessageContent
    override val fbReactions = binding.fbReactions

    override fun bind(item: ForeignMessageUi) {
        super.bind(item)
        binding.tvMessageAuthor.text = item.author
        Glide.with(root)
            .load(item.authorImageUrl)
            .into(binding.ivMessageAuthorPic)
    }
}
