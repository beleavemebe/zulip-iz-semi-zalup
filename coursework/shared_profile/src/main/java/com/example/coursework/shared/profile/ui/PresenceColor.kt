package com.example.coursework.shared.profile.ui

import androidx.annotation.ColorRes
import com.example.core_ui.R
import com.example.coursework.shared.profile.domain.UserPresence
import com.example.coursework.shared.profile.domain.UserPresence.*

@get:ColorRes
val UserPresence.color get() = when (this) {
    ACTIVE -> R.color.active
    IDLE -> R.color.idle
    OFFLINE -> R.color.offline
}
