package com.example.coursework.feature.people.ui.recycler

import android.view.View
import com.example.coursework.feature.people.R
import com.example.coursework.feature.people.ui.model.PeopleUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.base.CoroutinesHolderFactory

class PeopleViewHolderFactory : CoroutinesHolderFactory() {
    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<out PeopleUi> {
        return when (viewType) {
            R.layout.item_person -> PersonUiViewHolder(view)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }
}
