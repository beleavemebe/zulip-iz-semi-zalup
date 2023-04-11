package com.example.coursework.feature.profile.ui

import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.coursework.feature.profile.ui.elm.ProfileEffect
import com.example.coursework.feature.profile.ui.elm.ProfileEvent
import com.example.coursework.feature.profile.ui.elm.ProfileState
import com.example.coursework.profile.R
import com.example.coursework.profile.databinding.FragmentProfileBinding
import com.example.coursework.shared.profile.ui.color
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    override val initEvent = ProfileEvent.Init

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun render(state: ProfileState) {
        binding.progressIndicator.isVisible = state.isLoading
        val user = state.user ?: return

        Glide.with(this)
            .load(user.imageUrl)
            .into(binding.ivProfileImage)
        binding.tvUserName.text = user.name
        binding.tvUserOnline.text = user.presence.name.lowercase()
        binding.tvUserOnline.setTextColor(
            ResourcesCompat.getColor(
                requireView().resources,
                user.presence.color,
                requireActivity().theme
            )
        )
    }
}
