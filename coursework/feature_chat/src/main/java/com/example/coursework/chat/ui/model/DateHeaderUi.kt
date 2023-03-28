package com.example.coursework.chat.ui.model

import com.example.feature_chat.R
import java.time.LocalDate

data class DateHeaderUi(
    val date: LocalDate,
) : ChatItem {
    override val viewType: Int = R.layout.item_date_header
    override val uid: String = date.toString()
}
