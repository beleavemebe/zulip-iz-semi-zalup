package com.example.coursework.feature.profile.ui

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.core.ui.argument
import com.example.core.ui.assistedViewModel
import com.example.coursework.core.di.DaggerViewModelFactory
import com.example.coursework.feature.profile.ui.di.ProfileFacade
import com.example.coursework.feature.profile.ui.elm.ProfileEffect
import com.example.coursework.feature.profile.ui.elm.ProfileEvent
import com.example.coursework.feature.profile.ui.elm.ProfileState
import com.example.coursework.profile.R
import com.example.coursework.profile.databinding.FragmentProfileBinding
import com.example.coursework.shared.profile.ui.color
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>(R.layout.fragment_profile) {
    @Inject lateinit var daggerViewModelFactory: DaggerViewModelFactory
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by assistedViewModel {
        daggerViewModelFactory.create(ProfileViewModel::class.java)
    }
    private val userId: Int by argument(KEY_USER_ID)

    override val initEvent by lazy {
        ProfileEvent.Init(userId)
    }

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun onAttach(context: Context) {
        ProfileFacade.getComponent().inject(this)
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

    override fun onDestroy() {
        super.onDestroy()
        ProfileFacade.clear()
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"

        fun newInstance(userId: Int) = ProfileFragment().apply {
            arguments = bundleOf(KEY_USER_ID to userId)
        }
    }
}
