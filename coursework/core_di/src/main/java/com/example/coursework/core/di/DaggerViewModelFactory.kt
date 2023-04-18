package com.example.coursework.core.di

import androidx.lifecycle.ViewModel

interface DaggerViewModelFactory {
    fun <T : ViewModel> create(viewModelClass: Class<T>): T
}
