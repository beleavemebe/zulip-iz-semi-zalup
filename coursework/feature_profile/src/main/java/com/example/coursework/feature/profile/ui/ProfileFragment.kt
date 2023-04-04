package com.example.coursework.feature.profile.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.coursework.profile.R
import com.example.coursework.profile.databinding.FragmentProfileBinding
import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.User
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import com.example.coursework.shared.profile.ui.color
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val getCurrentUser = GetCurrentUser(UsersRepositoryImpl.instance)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            renderUser(getCurrentUser.execute())
        }
    }

    private fun renderUser(user: User) {
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
