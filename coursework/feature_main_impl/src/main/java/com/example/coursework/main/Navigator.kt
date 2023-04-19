package com.example.coursework.main

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class Navigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
) : AppNavigator(activity, R.id.local_container, fragmentManager) {
    override fun replace(command: Replace) {
        val screen = (command.screen as FragmentScreen)
        executeReplace(screen)
    }

    private fun executeReplace(screen: FragmentScreen) = with(fragmentManager) {
        val tag = screen.screenKey
        val fragment = findFragmentByTag(tag)
        val currentFragment = fragments.singleOrNull { it.isHidden.not() }
        beginTransaction().apply {
            if (currentFragment != null) {
                hide(currentFragment)
            }

            if (fragment != null) {
                show(fragment)
            } else {
                add(R.id.local_container, screen.createFragment(fragmentFactory), tag)
            }
        }.commit()
    }
}
