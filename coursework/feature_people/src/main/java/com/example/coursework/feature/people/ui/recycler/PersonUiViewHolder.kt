package com.example.coursework.feature.people.ui.recycler

import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.coursework.feature.people.databinding.ItemPersonBinding
import com.example.coursework.feature.people.ui.model.PeopleUi
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder

class PersonUiViewHolder(view: View) : BaseViewHolder<PeopleUi>(view) {
    private val binding = ItemPersonBinding.bind(view)

    override fun bind(item: PeopleUi) {
        binding.tvUserName.text = item.name
        binding.tvUserEmail.text = item.email
        binding.ivOnlineIndicator.isVisible = item.isOnline
        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivProfilePic)
    }
}
