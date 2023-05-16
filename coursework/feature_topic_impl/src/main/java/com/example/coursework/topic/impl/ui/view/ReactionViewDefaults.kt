package com.example.coursework.topic.impl.ui.view

import android.view.View
import com.example.core.ui.dp

object ReactionViewDefaults {
    val View.reactionViewWidth
        get() = dp(55f).toInt()

    val View.reactionViewHeight
        get() = dp(37f).toInt()

    val View.backgroundCornerRadius
        get() = dp(10f)
}
