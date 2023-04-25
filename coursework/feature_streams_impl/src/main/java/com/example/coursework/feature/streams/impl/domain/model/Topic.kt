package com.example.coursework.feature.streams.impl.domain.model

import android.graphics.Color

data class Topic(
    val name: String,
    val color: Int = Color.parseColor("#000000"),
)
