package com.example.coursework.feature.streams.impl.ui.elm

sealed interface StreamsEffect {
    data class ShowStreamCreatedToast(val name: String) : StreamsEffect
}
