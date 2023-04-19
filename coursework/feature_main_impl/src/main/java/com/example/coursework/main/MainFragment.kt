package com.example.coursework.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.main.databinding.FragmentMainBinding
import com.example.coursework.main.di.MainFacade
import com.github.terrakok.cicerone.Cicerone

class MainFragment : Fragment(R.layout.fragment_main) {
    private val cicerone = Cicerone.create()
    private val navigator by lazy { Navigator(requireActivity(), childFragmentManager) }
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            handleBottomNavClicked(R.id.item_streams)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            handleBottomNavClicked(menuItem.itemId)
            true
        }
    }

    private fun handleBottomNavClicked(itemId: Int) {
        val localRouter = cicerone.router
        val deps = MainFacade.deps
        when (itemId) {
            R.id.item_streams -> localRouter.replaceScreen(deps.getStreamsScreen())
            R.id.item_people -> localRouter.replaceScreen(deps.getPeopleScreen())
            R.id.item_profile -> localRouter.replaceScreen(deps.getProfileScreen())
        }
    }
}
