package com.example.coursework.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.main.databinding.FragmentMainBinding
import com.example.coursework.main.di.MainFacade
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    @Inject lateinit var cicerone: Cicerone<Router>
    private val navigator by lazy { Navigator(requireActivity(), childFragmentManager) }
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onAttach(context: Context) {
        MainFacade.component.inject(this)
        super.onAttach(context)
    }

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
        val deps = MainFacade.deps
        when (itemId) {
            R.id.item_streams -> cicerone.router.replaceScreen(deps.getStreamsScreen())
            R.id.item_people -> cicerone.router.replaceScreen(deps.getPeopleScreen())
            R.id.item_profile -> cicerone.router.replaceScreen(deps.getProfileScreen())
        }
    }
}
