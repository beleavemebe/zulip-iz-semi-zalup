package com.example.core.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

inline fun <reified VM : ViewModel> Fragment.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> VM
) = viewModels<VM> {
    object : AbstractSavedStateViewModelFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle,
        ): T {
            return viewModelProducer(handle) as T
        }
    }
}

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun <T> Fragment.argument(key: String) = lazy {
    requireNotNull(arguments?.get(key)) as T
}
