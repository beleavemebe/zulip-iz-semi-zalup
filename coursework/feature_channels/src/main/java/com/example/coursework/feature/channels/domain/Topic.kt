package com.example.coursework.feature.channels.domain

import android.graphics.Color

data class Topic(
    val name: String,
    val messageCount: Int,
    val color: Int = Color.parseColor("#000000"),
)
