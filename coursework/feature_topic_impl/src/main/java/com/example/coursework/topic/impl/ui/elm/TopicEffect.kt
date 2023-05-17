package com.example.coursework.topic.impl.ui.elm

sealed interface TopicEffect {
    data class CopyMessage(val message: String) : TopicEffect
}
