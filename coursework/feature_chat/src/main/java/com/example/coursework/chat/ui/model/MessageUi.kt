package com.example.coursework.chat.ui.recycler

import android.view.View
import com.example.coursework.chat.ui.model.MessageModel
import com.example.feature_chat.R
import com.example.feature_chat.databinding.LayoutMessageBinding
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

data class MessageUi(
    val model: MessageModel,
    override val viewType: Int = R.layout.layout_message,
    override val uid: String
) : ViewTyped

class MessageUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<MessageUi>(view, clicks) {
    private val binding = LayoutMessageBinding.bind(view)
}
