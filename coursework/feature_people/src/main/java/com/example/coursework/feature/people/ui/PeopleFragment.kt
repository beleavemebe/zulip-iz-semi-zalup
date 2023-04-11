package com.example.coursework.feature.people.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.doOnQueryChanged
import com.example.coursework.feature.people.R
import com.example.coursework.feature.people.databinding.FragmentPeopleBinding
import com.example.coursework.feature.people.ui.elm.PeopleEffect
import com.example.coursework.feature.people.ui.elm.PeopleEvent
import com.example.coursework.feature.people.ui.elm.PeopleState
import com.example.coursework.feature.people.ui.recycler.PeopleViewHolderFactory
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>(R.layout.fragment_people) {
    private val binding by viewBinding(FragmentPeopleBinding::bind)
    private val viewModel by viewModels<PeopleViewModel>()
    private val factory = PeopleViewHolderFactory()
    private val recycler by lazy {
        TiRecyclerCoroutines(
            binding.rvUsers,
            AsyncTiAdapter(factory, ViewTypedDiffCallback()),
        )
    }

    override val initEvent = PeopleEvent.Init

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override fun render(state: PeopleState) {
        binding.tvError.isVisible = state.error != null
        binding.tvError.text = state.error.toString()
        binding.progressIndicator.isVisible = state.isLoading
        recycler.adapter.items = state.people
        binding.tvNotFound.isVisible = state.notFound
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
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
}
