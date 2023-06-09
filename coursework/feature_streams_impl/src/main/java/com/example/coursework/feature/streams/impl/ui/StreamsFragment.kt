package com.example.coursework.feature.streams.impl.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.ui.assistedViewModel
import com.example.core.ui.doOnQueryChanged
import com.example.coursework.core.utils.collectWhenStarted
import com.example.coursework.feature.streams.R
import com.example.coursework.feature.streams.databinding.FragmentStreamsBinding
import com.example.coursework.feature.streams.impl.StreamsFacade
import com.example.coursework.feature.streams.impl.ui.elm.StreamsEffect
import com.example.coursework.feature.streams.impl.ui.elm.StreamsEvent
import com.example.coursework.feature.streams.impl.ui.elm.StreamsState
import com.example.coursework.feature.streams.impl.ui.elm.StreamsStoreFactory
import com.example.coursework.feature.streams.impl.ui.model.*
import com.example.coursework.feature.streams.impl.ui.recycler.StreamsViewHolderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import javax.inject.Inject

class StreamsFragment : ElmFragment<StreamsEvent, StreamsEffect, StreamsState>(R.layout.fragment_streams) {
    @Inject lateinit var storeFactory: StreamsStoreFactory
    private val viewModel by assistedViewModel { StreamsViewModel(storeFactory) }
    private val binding by viewBinding(FragmentStreamsBinding::bind)
    private lateinit var recycler: TiRecyclerCoroutines<StreamsItem>

    override val storeHolder by lazy {
        LifecycleAwareStoreHolder(lifecycle, viewModel::store)
    }

    override val initEvent = StreamsEvent.Init

    override fun render(state: StreamsState) {
        binding.tvError.isVisible = state.error != null
        binding.tvError.text = state.error.toString()
        binding.progressIndicator.isVisible = state.isLoading
        recycler.adapter.items = state.items
    }

    override fun handleEffect(effect: StreamsEffect) =
        when (effect) {
            is StreamsEffect.ShowStreamCreatedToast -> showStreamCreatedToast(effect)
        }

    override fun onAttach(context: Context) {
        StreamsFacade.component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initTabListener()
        initSearchView()
        initCreateStreamButton()
    }

    private fun initRecycler() {
        recycler = TiRecyclerCoroutines(
            binding.rvChannels,
            AsyncTiAdapter(
                StreamsViewHolderFactory(),
                ViewTypedDiffCallback(StreamsItem.payloadMappers)
            )
        ).apply(::handleClicks)
    }

    private fun handleClicks(recycler: TiRecyclerCoroutines<StreamsItem>) {
        recycler.clickedItem<StreamUi>(StreamUi.VIEW_TYPE)
            .onEach(viewModel::clickStream)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)

        recycler.clickedItem<TopicUi>(TopicUi.VIEW_TYPE)
            .onEach(viewModel::clickTopic)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)

        recycler.clickedItem<ViewAllMessagesUi>(ViewAllMessagesUi.VIEW_TYPE)
            .onEach(viewModel::clickViewAllMessages)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)

        recycler.clickedItem<CreateTopicUi>(CreateTopicUi.VIEW_TYPE)
            .onEach(viewModel::clickCreateTopic)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun initSearchView() {
        val searchView = binding.toolbar.menu.findItem(R.id.menu_item_search)
            .actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.find_streams)
        searchView.doOnQueryChanged { query ->
            viewModel.searchQuery.value = query
        }
    }

    private fun initCreateStreamButton() {
        binding.toolbar.menu.findItem(R.id.menu_item_create_stream).setOnMenuItemClickListener {
            viewModel.clickCreateStream()
            true
        }
    }

    private fun initTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    TAB_SUBSCRIBED -> viewModel.selectStreamsTab(StreamsTab.SUBSCRIBED)
                    TAB_ALL -> viewModel.selectStreamsTab(StreamsTab.ALL)
                }
            }
        })
    }

    private fun showStreamCreatedToast(effect: StreamsEffect.ShowStreamCreatedToast) {
        Toast.makeText(
            requireContext(),
            getString(R.string.placeholder_stream_created, effect.name),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val TAB_SUBSCRIBED = 0
        const val TAB_ALL = 1
    }
}
