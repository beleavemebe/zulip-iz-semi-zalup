package com.example.coursework.chat.ui.recycler

import android.view.View
import com.example.feature_chat.R
import com.example.feature_chat.databinding.LayoutDateHeaderBinding
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener
import java.time.LocalDate

data class DateHeaderUi(
    val date: LocalDate,
    override val viewType: Int = R.layout.layout_date_header,
    override val uid: String = date.toString()
) : ViewTyped


class DateHeaderUiViewHolder(
    view: View,
    clicks: TiRecyclerClickListener
) : BaseViewHolder<DateHeaderUi>(view, clicks) {
    private val binding = LayoutDateHeaderBinding.bind(view)

    override fun bind(item: DateHeaderUi) {
        binding.root.text = item.date.toString()
    }
}
