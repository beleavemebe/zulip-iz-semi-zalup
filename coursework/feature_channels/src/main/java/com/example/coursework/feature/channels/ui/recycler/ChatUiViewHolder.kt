package com.example.coursework.feature.channels.ui.recycler

import android.graphics.drawable.ColorDrawable
import android.view.View
import com.example.coursework.feature.channels.R
import com.example.coursework.feature.channels.databinding.ItemChatBinding
import com.example.coursework.feature.channels.ui.model.ChatUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class ChatUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<ChatUi>(view) {
    private val binding = ItemChatBinding.bind(view)

    init {
        clicks.accept(this)
    }

    override fun bind(item: ChatUi) {
        val context = binding.root.context
        val background = binding.root.background as? ColorDrawable
            ?: ColorDrawable().also(binding.root::setBackground)
        binding.tvChatName.text = item.name
        binding.tvMessageCount.text = context.getString(R.string.message_count_placeholder, item.messageCount)
        background.color = item.color
    }
}
