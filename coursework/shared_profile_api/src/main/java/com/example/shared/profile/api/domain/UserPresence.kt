package com.example.shared.profile.api.domain

import androidx.annotation.ColorRes
import com.example.core_ui.R

enum class UserPresence(
    @ColorRes val color: Int,
) {
    ACTIVE(R.color.active),
    IDLE(R.color.idle),
    OFFLINE(R.color.offline)
}
