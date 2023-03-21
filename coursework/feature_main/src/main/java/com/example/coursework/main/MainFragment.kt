package com.example.coursework.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.core.di.ServiceLocator
import com.example.coursework.main.databinding.FragmentMainBinding
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainFragment : Fragment(R.layout.fragment_main) {
    private val cicerone = Cicerone.create()
    private val navigator by lazy { AppNavigator(requireActivity(), R.id.local_container) }
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.localRouter = cicerone.router
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screens = ServiceLocator.screens
        cicerone.router.replaceScreen(screens.channels())
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            handleBottomNavClicked(menuItem)
            true
        }
    }

    private fun handleBottomNavClicked(menuItem: MenuItem) {
        val localRouter = cicerone.router
        val screens = ServiceLocator.screens
        when (menuItem.itemId) {
            R.id.item_channels -> localRouter.replaceScreen(screens.channels())
            R.id.item_people -> localRouter.replaceScreen(screens.people())
            R.id.item_profile -> localRouter.replaceScreen(screens.profile())
        }
    }
}
