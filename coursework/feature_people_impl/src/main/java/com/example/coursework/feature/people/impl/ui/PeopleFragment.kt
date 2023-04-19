package com.example.coursework.feature.people.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.assistedViewModel
import com.example.core.ui.doOnQueryChanged
import com.example.coursework.feature.people.impl.PeopleFacade
import com.example.coursework.feature.people.impl.R
import com.example.coursework.feature.people.impl.databinding.FragmentPeopleBinding
import com.example.coursework.feature.people.impl.ui.elm.PeopleEffect
import com.example.coursework.feature.people.impl.ui.elm.PeopleEvent
import com.example.coursework.feature.people.impl.ui.elm.PeopleState
import com.example.coursework.feature.people.impl.ui.elm.PeopleStoreFactory
import com.example.coursework.feature.people.impl.ui.recycler.PeopleViewHolderFactory
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>(R.layout.fragment_people) {
    @Inject lateinit var storeFactory: PeopleStoreFactory
    private val binding by viewBinding(FragmentPeopleBinding::bind)
    private val viewModel by assistedViewModel { PeopleViewModel(storeFactory) }
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

    override fun onAttach(context: Context) {
        PeopleFacade.component.inject(this)
        super.onAttach(context)
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
