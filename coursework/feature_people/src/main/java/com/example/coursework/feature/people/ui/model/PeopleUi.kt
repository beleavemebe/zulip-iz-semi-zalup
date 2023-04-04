package com.example.coursework.feature.people.ui.model

import com.example.coursework.feature.people.R
import com.example.coursework.shared.profile.domain.UserPresence
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped

data class PeopleUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val presence: UserPresence,
    val email: String
) : ViewTyped {
    override val uid: String = id.toString()
    override val viewType: Int = R.layout.item_person
}
