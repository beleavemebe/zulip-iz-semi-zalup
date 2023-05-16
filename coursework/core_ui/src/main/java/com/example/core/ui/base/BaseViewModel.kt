package com.example.core.ui.base

import androidx.lifecycle.ViewModel
import com.example.coursework.core.utils.CacheContainer

abstract class BaseViewModel : ViewModel(), CacheContainer by CacheContainer.Map()
