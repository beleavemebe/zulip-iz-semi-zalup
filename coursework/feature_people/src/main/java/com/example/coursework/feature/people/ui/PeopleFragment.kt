package com.example.coursework.feature.people.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.core.utils.collectWhenStarted
import com.example.coursework.feature.people.R
import com.example.coursework.feature.people.databinding.FragmentPeopleBinding
import com.example.coursework.feature.people.ui.model.PeopleState
import com.example.coursework.feature.people.ui.recycler.PeopleViewHolderFactory
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines

class PeopleFragment : Fragment(R.layout.fragment_people) {
    private val binding by viewBinding(FragmentPeopleBinding::bind)
    private val viewModel by viewModels<PeopleViewModel>()
    private val factory = PeopleViewHolderFactory()
    private val recycler by lazy {
        TiRecyclerCoroutines(
            binding.rvUsers,
            AsyncTiAdapter(factory, ViewTypedDiffCallback()),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectInputs()
        subscribeToState()
    }

    private fun collectInputs() {
        binding.searchView.listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery.value = newText.orEmpty()
                return true
            }
        }
    }

    private fun subscribeToState() {
        viewModel.state
            .onEach(::renderState)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun renderState(state: PeopleState) {
        recycler.adapter.items = state.people
        binding.tvNotFound.isVisible = state.notFound
    }
}
