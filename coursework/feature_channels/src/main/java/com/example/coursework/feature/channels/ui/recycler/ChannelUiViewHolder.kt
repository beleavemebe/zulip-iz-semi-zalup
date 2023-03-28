package com.example.coursework.feature.channels.ui.recycler

import android.view.View
import com.example.coursework.feature.channels.databinding.ItemChannelBinding
import com.example.coursework.feature.channels.ui.model.ChannelUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class ChannelUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<ChannelUi>(view) {
    private val binding = ItemChannelBinding.bind(view)
    private var cachedExpanded = false

    init {
        clicks.accept(this) {
            cachedExpanded = cachedExpanded.not()
            renderExpanded()
        }
    }

    override fun bind(item: ChannelUi) {
        binding.tvChannelName.text = item.tag
        cachedExpanded = item.isExpanded
        renderExpanded()
    }

    private fun renderExpanded() {
        val ivState = if (cachedExpanded) android.R.attr.state_checked else android.R.attr.state_checkable
        binding.ivExpandCollapse.setImageState(intArrayOf(ivState), true)
    }
}