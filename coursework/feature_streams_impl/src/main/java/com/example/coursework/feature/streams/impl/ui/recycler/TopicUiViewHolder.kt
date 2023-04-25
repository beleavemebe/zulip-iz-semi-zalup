package com.example.coursework.feature.streams.impl.ui.recycler

import android.graphics.drawable.ColorDrawable
import android.view.View
import com.example.coursework.feature.streams.databinding.ItemTopicBinding
import com.example.coursework.feature.streams.impl.ui.model.TopicUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class TopicUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<TopicUi>(view) {
    private val binding = ItemTopicBinding.bind(view)

    init {
        clicks.accept(this)
    }

    override fun bind(item: TopicUi) {
        val background = binding.root.background as? ColorDrawable
            ?: ColorDrawable().also(binding.root::setBackground)
        binding.tvTopicName.text = item.name
        background.color = item.color
    }
}
