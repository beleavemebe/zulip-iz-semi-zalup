package com.example.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.core.utils.CacheContainer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CacheContainer by CacheContainer.Map() {
    protected val coroutineScope = CoroutineScope(createCoroutineContext())

    protected open fun handleException(throwable: Throwable) = Unit

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    private fun createCoroutineContext(): CoroutineContext {
        return viewModelScope.coroutineContext + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            handleException(throwable)
        }
    }
}
