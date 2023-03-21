package com.example.coursework.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coursework.core.di.ServiceLocator
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity : AppCompatActivity() {
    private val cicerone = Cicerone.create()
    private val navigator = AppNavigator(this, R.id.global_container)

    init {
        ServiceLocator.screens = ScreensImpl()
        ServiceLocator.globalRouter = cicerone.router
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            cicerone.router.replaceScreen(ServiceLocator.screens.main())
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.getNavigatorHolder().removeNavigator()
    }
}
