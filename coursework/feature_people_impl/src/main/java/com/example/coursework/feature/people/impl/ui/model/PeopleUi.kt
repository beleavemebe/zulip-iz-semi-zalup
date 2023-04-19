package com.example.coursework.feature.people.impl.ui.model

import com.example.coursework.feature.people.impl.R
import com.example.shared.profile.api.domain.UserPresence
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
