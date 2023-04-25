package com.example.coursework.topic.impl.ui.model

import com.example.feature.topic.impl.R
import java.time.LocalDate

data class DateHeaderUi(
    val date: LocalDate,
) : TopicItem {
    override val viewType: Int = VIEW_TYPE
    override val uid: String = date.toString()

    companion object {
        val VIEW_TYPE = R.layout.item_date_header
    }
}
