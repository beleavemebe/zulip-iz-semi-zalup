package com.example.dating.shared.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("unused")
fun <T> Flow<T>.collectWhenStarted(lifecycle: Lifecycle) = collectWhen(lifecycle, STARTED)

fun <T> Flow<T>.collectWhenResumed(lifecycle: Lifecycle) = collectWhen(lifecycle, RESUMED)

private fun <T> Flow<T>.collectWhen(
    lifecycle: Lifecycle,
    state: State
) {
    val flow = this
    lifecycle.coroutineScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            flow.collect()
        }
    }
}
