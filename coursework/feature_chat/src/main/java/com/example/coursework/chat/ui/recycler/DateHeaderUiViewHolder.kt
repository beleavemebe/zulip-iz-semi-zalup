package com.example.coursework.chat.ui.recycler

import android.view.View
import com.example.coursework.chat.ui.model.DateHeaderUi
import com.example.feature_chat.databinding.ItemDateHeaderBinding
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DateHeaderUiViewHolder(
    view: View
) : BaseViewHolder<DateHeaderUi>(view) {
    private val binding = ItemDateHeaderBinding.bind(view)

    override fun bind(item: DateHeaderUi) {
        binding.tvDate.text = formatter.format(item.date)
    }

    companion object {
        private val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    }
}
