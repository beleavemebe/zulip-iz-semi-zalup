package com.example.coursework.feature.profile.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.core.ui.HighlightOnPressBackground
import com.example.coursework.profile.R
import com.example.coursework.profile.databinding.FragmentProfileBinding
import com.example.coursework.feature.profile.domain.GetCurrentUser
import com.example.coursework.shared.profile.domain.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderUser(GetCurrentUser().execute())
    }

    private fun renderUser(user: User) {
        Glide.with(this)
            .load(user.imageUrl)
            .into(binding.ivProfileImage)
        binding.tvUserName.text = user.name
        binding.tvUserStatus.text = user.status
        binding.tvUserOnline.text = user.onlineStatus.name.lowercase()
        binding.btnLogout.background = HighlightOnPressBackground(999f)
    }
}
