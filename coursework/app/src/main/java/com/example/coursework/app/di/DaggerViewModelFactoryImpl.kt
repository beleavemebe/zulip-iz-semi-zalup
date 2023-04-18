package com.example.coursework.app.di

import androidx.lifecycle.ViewModel
import com.example.coursework.core.di.DaggerViewModelFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactoryImpl @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : DaggerViewModelFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        return providers.getValue(viewModelClass as Class<out ViewModel>).get() as T
    }
}
