package com.example.core.ui.base

import androidx.lifecycle.ViewModel
import com.example.coursework.core.utils.CacheContainer
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CacheContainer by CacheContainer.Map() {
    protected val coroutineScope = CoroutineScope(createCoroutineContext())

    protected open fun handleException(throwable: Throwable) = Unit

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    private fun createCoroutineContext(): CoroutineContext {
        return SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            handleException(throwable)
        }
    }
}
