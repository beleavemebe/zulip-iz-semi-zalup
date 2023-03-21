package com.example.coursework.feature.people.ui.model

import com.example.coursework.feature.people.R
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

data class PeopleUi(
    val id: String,
    val name: String,
    val imageUrl: String,
    val isOnline: Boolean,
    val email: String
) : ViewTyped {
    override val uid: String = id
    override val viewType: Int = R.layout.item_person
}
