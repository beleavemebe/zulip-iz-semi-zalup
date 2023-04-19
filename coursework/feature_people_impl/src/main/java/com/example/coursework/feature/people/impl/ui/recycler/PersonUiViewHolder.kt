package com.example.coursework.feature.people.impl.ui.recycler

import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.coursework.feature.people.impl.databinding.ItemPersonBinding
import com.example.coursework.feature.people.impl.ui.model.PeopleUi
import com.example.shared.profile.api.ui.color
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
