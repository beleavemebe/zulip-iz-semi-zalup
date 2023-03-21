package com.example.coursework.core.di

import com.example.core.ui.Screens
import com.github.terrakok.cicerone.Router
import kotlin.properties.Delegates

object ServiceLocator {
    var screens: Screens by Delegates.notNull()
    var globalRouter: Router by Delegates.notNull()
    var localRouter: Router by Delegates.notNull()
}
