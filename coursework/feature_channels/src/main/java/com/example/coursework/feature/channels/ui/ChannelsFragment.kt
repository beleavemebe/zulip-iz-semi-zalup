package com.example.coursework.feature.channels.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coursework.core.utils.collectWhenStarted
import com.example.coursework.feature.channels.R
import com.example.coursework.feature.channels.databinding.FragmentChannelsBinding
import com.example.coursework.feature.channels.ui.model.ChannelUi
import com.example.coursework.feature.channels.ui.model.ChannelsItem
import com.example.coursework.feature.channels.ui.model.ChannelsState
import com.example.coursework.feature.channels.ui.model.ChatUi
import com.example.coursework.feature.channels.ui.recycler.ChannelsViewHolderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.flow.onEach
import ru.tinkoff.mobile.tech.ti_recycler.adapters.AsyncTiAdapter
import ru.tinkoff.mobile.tech.ti_recycler.base.diff.ViewTypedDiffCallback
import ru.tinkoff.mobile.tech.ti_recycler_coroutines.TiRecyclerCoroutines

class ChannelsFragment : Fragment(R.layout.fragment_channels) {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory.instance.create(ChannelsViewModel::class.java)
    }
    private val binding by viewBinding(FragmentChannelsBinding::bind)
    private val factory = ChannelsViewHolderFactory()
    private val recycler by lazy {
        TiRecyclerCoroutines<ChannelsItem>(
            binding.rvChannels,
            AsyncTiAdapter(factory, ViewTypedDiffCallback())
        ).apply(::handleClicks)
    }

    private fun handleClicks(recycler: TiRecyclerCoroutines<ChannelsItem>) {
        recycler.clickedItem<ChannelUi>(ChannelUi.VIEW_TYPE)
            .onEach(viewModel::clickChannel)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)

        recycler.clickedItem<ChatUi>(ChatUi.VIEW_TYPE)
            .onEach(viewModel::clickChat)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabListener()
        observeState()
    }

    private fun initTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> viewModel.showSubscribedStreams()
                    1 -> viewModel.showAllStreams()
                }
            }
        })
    }

    private fun observeState() {
        viewModel.state
            .onEach(::renderState)
            .collectWhenStarted(viewLifecycleOwner.lifecycle)
    }

    private fun renderState(state: ChannelsState) {
        recycler.adapter.items = state.items
    }
}