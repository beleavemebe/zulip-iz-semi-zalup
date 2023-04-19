package com.example.shared.profile.api.ui

import androidx.annotation.ColorRes
import com.example.core_ui.R
import com.example.shared.profile.api.domain.UserPresence
import com.example.shared.profile.api.domain.UserPresence.*

@get:ColorRes
val UserPresence.color get() = when (this) {
    ACTIVE -> R.color.active
    IDLE -> R.color.idle
    OFFLINE -> R.color.offline
}
