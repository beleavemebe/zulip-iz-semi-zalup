package com.example.coursework.core.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> Flow<T>.collectWhenStarted(lifecycle: Lifecycle) = collectWhen(lifecycle, STARTED)

private fun <T> Flow<T>.collectWhen(
    lifecycle: Lifecycle,
    state: State
) {
    val flow = this
    lifecycle.coroutineScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            withContext(Dispatchers.Main) {
                flow.collect()
            }
        }
    }
}
