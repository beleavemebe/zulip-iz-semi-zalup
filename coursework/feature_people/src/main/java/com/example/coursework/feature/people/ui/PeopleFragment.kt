package com.example.coursework.feature.people.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.doOnQueryChanged
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
        initSearchView()
        subscribeToState()
    }

    private fun initSearchView() {
        val searchView = binding.toolbar.menu.findItem(R.id.menu_item_search)
            .actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.find_users)
        searchView.doOnQueryChanged { query ->
            viewModel.searchQuery.value = query
        }
    }

    private fun subscribeToState() {
        viewModel.state
            .onEach(::renderState)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun renderState(state: PeopleState) {
        binding.tvError.isVisible = state.error != null
        binding.tvError.text = state.error.toString()
        binding.progressIndicator.isVisible = state.isLoading
        recycler.adapter.items = state.people
        binding.tvNotFound.isVisible = state.notFound
    }
}
