package com.example.coursework.feature.profile.ui

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.core.ui.argument
import com.example.core.ui.assistedViewModel
import com.example.coursework.feature.profile.ui.di.ProfileFacade
import com.example.coursework.feature.profile.ui.elm.ProfileEffect
import com.example.coursework.feature.profile.ui.elm.ProfileEvent
import com.example.coursework.feature.profile.ui.elm.ProfileState
import com.example.coursework.feature.profile.ui.elm.ProfileStoreFactory
import com.example.coursework.profile.R
import com.example.coursework.profile.databinding.FragmentProfileBinding
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>(R.layout.fragment_profile) {
    @Inject lateinit var storeFactory: ProfileStoreFactory
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by assistedViewModel { ProfileViewModel(storeFactory) }
    private val userId: Int by argument(KEY_USER_ID)

    override val initEvent by lazy {
        ProfileEvent.Init(userId)
    }

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun onAttach(context: Context) {
        ProfileFacade.component.inject(this)
        super.onAttach(context)
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

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"

        fun newInstance(userId: Int) = ProfileFragment().apply {
            arguments = bundleOf(KEY_USER_ID to userId)
        }
    }
}
