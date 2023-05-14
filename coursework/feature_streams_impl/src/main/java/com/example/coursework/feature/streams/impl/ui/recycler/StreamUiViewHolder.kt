package com.example.coursework.feature.streams.impl.ui.recycler

import android.view.View
import com.example.coursework.feature.streams.databinding.ItemStreamBinding
import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

class StreamUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<StreamUi>(view, clicks) {
    private val binding = ItemStreamBinding.bind(view)

    override fun bind(item: StreamUi, payload: List<Any>) {
        val isExpandedPayload = payload.getOrNull(0) as? Boolean
        if (isExpandedPayload != null) {
            renderExpanded(isExpandedPayload)
        } else {
            super.bind(item, payload)
        }
    }

    override fun bind(item: StreamUi) {
        binding.tvStreamName.text = item.name
        renderExpanded(item.isExpanded)
    }

    private fun renderExpanded(isExpanded: Boolean) {
        val ivState = if (isExpanded) android.R.attr.state_checked else android.R.attr.state_checkable
        binding.ivExpandCollapse.setImageState(intArrayOf(ivState), true)
    }
}
