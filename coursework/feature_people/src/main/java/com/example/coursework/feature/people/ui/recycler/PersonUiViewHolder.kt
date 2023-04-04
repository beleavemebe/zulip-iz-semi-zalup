package com.example.coursework.feature.people.ui.recycler

import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.coursework.feature.people.databinding.ItemPersonBinding
import com.example.coursework.feature.people.ui.model.PeopleUi
import com.example.coursework.shared.profile.ui.color
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder

class PersonUiViewHolder(view: View) : BaseViewHolder<PeopleUi>(view) {
    private val binding = ItemPersonBinding.bind(view)

    override fun bind(item: PeopleUi) {
        binding.tvUserName.text = item.name
        binding.tvUserEmail.text = item.email
        binding.ivPresenceIndicator.drawable.setTint(getPresenceColor(item))
        Glide.with(binding.root)
            .load(item.imageUrl)
            .into(binding.ivProfilePic)
    }

    @ColorInt
    private fun getPresenceColor(item: PeopleUi): Int {
        return ResourcesCompat.getColor(
            itemView.resources,
            item.presence.color,
            itemView.context.theme
        )
    }
}
