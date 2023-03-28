@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coursework.feature.channels.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.core.di.ServiceLocator
import com.example.coursework.feature.channels.domain.Channel
import com.example.coursework.feature.channels.domain.GetAllStreams
import com.example.coursework.feature.channels.domain.GetSubscribedStreams
import com.example.coursework.feature.channels.ui.model.*
import com.example.coursework.shared.chat.Chat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ChannelsViewModel : BaseViewModel() {
    private val getAllStreams = GetAllStreams()
    private val getSubscribedStreams = GetSubscribedStreams()
    private val router = ServiceLocator.globalRouter
    private val screens = ServiceLocator.screens
    private val _state = MutableStateFlow(ChannelsState())

    val state = _state.asStateFlow()
    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
        loadChannels()
    }

    private fun observeSearchQuery() {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest { query -> updateState(query, _state.value.streamsTab) }
            .flowOn(Dispatchers.Default)
            .launchIn(coroutineScope)
    }

    private fun loadChannels() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                isLoading = false,
                items = getSubscribedStreams.execute().map(::toChannelUi)
            )
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        _state.value = _state.value.copy(
            isLoading = false,
            error = throwable,
        )
    }

    fun clickChannel(channelUi: ChannelUi) {
        if (channelUi.isExpanded.not()) {
            showChatsForChannel(channelUi)
            channelUi.isExpanded = true
        } else {
            hideChatsOfChannel(channelUi)
            channelUi.isExpanded = false
        }
    }

    private fun showChatsForChannel(channelUi: ChannelUi) {
        val oldItems = state.value.items
        val iofChannel = oldItems.indexOf(channelUi)
        val newItems: List<ChannelsItem> =
            oldItems.slice(0 until iofChannel) +
                    channelUi + channelUi.chatUis +
                    oldItems.slice(iofChannel + 1 until oldItems.size)
        _state.value = _state.value.copy(items = newItems)
    }

    private fun hideChatsOfChannel(channelUi: ChannelUi) {
        val oldItems = state.value.items
        val newItems = oldItems.filterNot { it in channelUi.chatUis }.toMutableList()
        _state.value = _state.value.copy(items = newItems.toList())
    }

    fun clickChat(chatUi: ChatUi) {
        router.navigateTo(screens.chat())
    }

    fun selectStreamsTab(tab: StreamsTab) {
        updateState(searchQuery.value, tab)
    }

    private fun updateState(
        searchQuery: String?,
        tab: StreamsTab
    ) {
        coroutineScope.launch(Dispatchers.Default) {
            _state.value = _state.value.copy(isLoading = true, streamsTab = tab)
            val items = getItems(searchQuery, tab)
            _state.value = _state.value.copy(
                isLoading = false,
                items = items,
                error = null
            )
        }
    }

    private suspend fun getItems(searchQuery: String?, tab: StreamsTab): List<ChannelsItem> {
        val items = when (tab) {
            StreamsTab.ALL -> getAllStreams()
            StreamsTab.SUBSCRIBED -> getSubscribedStreams()
        }
        if (searchQuery.isNullOrBlank()) {
            return items
        }
        return matchChannels(searchQuery, items)
    }

    private suspend fun getAllStreams() = withContext(Dispatchers.Default) {
        getAllStreams.execute().map(::toChannelUi)
    }


    private suspend fun getSubscribedStreams() = withContext(Dispatchers.Default) {
        getSubscribedStreams.execute().map(::toChannelUi)
    }

    private fun toChannelUi(
        channel: Channel
    ) = ChannelUi(
        tag = channel.name,
        isExpanded = false,
        chatUis = channel.chats.map(::toChatUi)
    )

    private fun toChatUi(
        chat: Chat
    ) = ChatUi(
        id = chat.id,
        name = chat.name,
        messageCount = chat.messageCount,
        color = chat.color
    )

    private fun matchChannels(
        query: String,
        channels: List<ChannelUi>
    ): List<ChannelsItem> = channels
        .filter { channelUi ->
            query.lowercase() in channelUi.tag.lowercase()
        }
        .flatMap { channelUi ->
            if (channelUi.isExpanded) {
                listOf(channelUi) + channelUi.chatUis
            } else {
                listOf(channelUi)
            }
        }
}
