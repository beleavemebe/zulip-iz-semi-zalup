package com.example.coursework.feature.channels.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.doOnQueryChanged
import com.example.coursework.core.utils.collectWhenStarted
import com.example.coursework.feature.channels.R
import com.example.coursework.feature.channels.databinding.FragmentStreamsBinding
import com.example.coursework.feature.channels.ui.model.*
import com.example.coursework.feature.channels.ui.recycler.StreamsViewHolderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines

class StreamsFragment : Fragment(R.layout.fragment_streams) {
    private val viewModel by viewModels<StreamsViewModel>()
    private val binding by viewBinding(FragmentStreamsBinding::bind)
    private val factory = StreamsViewHolderFactory()
    private val recycler by lazy {
        TiRecyclerCoroutines<StreamsItem>(
            binding.rvChannels,
            AsyncTiAdapter(factory, ViewTypedDiffCallback())
        ).apply(::handleClicks)
    }

    private fun handleClicks(recycler: TiRecyclerCoroutines<StreamsItem>) {
        recycler.clickedItem<StreamUi>(StreamUi.VIEW_TYPE)
            .onEach(viewModel::clickStream)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)

        recycler.clickedItem<TopicUi>(TopicUi.VIEW_TYPE)
            .onEach(viewModel::clickChat)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabListener()
        initSearchView()
        observeState()
    }

    private fun initSearchView() {
        val searchView = binding.toolbar.menu.findItem(R.id.menu_item_search)
            .actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.find_channels)
        searchView.doOnQueryChanged { query ->
            viewModel.searchQuery.value = query
        }
    }

    private fun initTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.selectStreamsTab(StreamsTab.SUBSCRIBED)
                    1 -> viewModel.selectStreamsTab(StreamsTab.ALL)
                }
            }
        })
    }

    private fun observeState() {
        viewModel.state
            .onEach(::renderState)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun renderState(state: StreamsState) {
        binding.tvError.isVisible = state.error != null
        binding.tvError.text = state.error.toString()
        binding.progressIndicator.isVisible = state.isLoading
        recycler.adapter.items = state.items
    }
}
