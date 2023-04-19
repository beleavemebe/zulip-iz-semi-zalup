package com.example.coursework.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coursework.core.di.GlobalCicerone
import com.example.feature.main.api.MainApi
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @[Inject GlobalCicerone]
    lateinit var cicerone: Cicerone<Router>
    @Inject lateinit var mainApi: MainApi
    private val navigator = AppNavigator(this, R.id.global_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            cicerone.router.replaceScreen(mainApi.getMainScreen())
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
