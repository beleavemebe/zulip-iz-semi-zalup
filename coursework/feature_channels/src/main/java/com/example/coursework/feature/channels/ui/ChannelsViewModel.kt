@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coursework.feature.channels.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.core.di.ServiceLocator
import com.example.coursework.feature.channels.data.StreamsRepositoryImpl
import com.example.coursework.feature.channels.domain.GetAllStreams
import com.example.coursework.feature.channels.domain.GetSubscribedStreams
import com.example.coursework.feature.channels.domain.GetTopicsForStream
import com.example.coursework.feature.channels.domain.Stream
import com.example.coursework.feature.channels.ui.model.*
import com.example.coursework.shared.chat.Topic
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ChannelsViewModel : BaseViewModel() {
    private val repository = StreamsRepositoryImpl.instance
    private val getAllStreams = GetAllStreams(repository)
    private val getSubscribedStreams = GetSubscribedStreams(repository)
    private val getTopicsForStream = GetTopicsForStream(repository)

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

    fun clickChannel(streamUi: StreamUi) {
        coroutineScope.launch(Dispatchers.Default) {
            if (streamUi.isExpanded.not()) {
                val topicUis = getTopicsForStream(streamUi.id)
                showTopicsForChannel(streamUi, topicUis)
                streamUi.isExpanded = true
            } else {
                hideChatsOfChannel(streamUi)
                streamUi.isExpanded = false
            }
        }
    }

    private fun showTopicsForChannel(
        streamUi: StreamUi,
        topicUis: List<TopicUi>,
    ) {
        val oldItems = state.value.items
        val iofChannel = oldItems.indexOf(streamUi)
        val newItems: List<ChannelsItem> =
            oldItems.slice(0 until iofChannel) +
                    streamUi + topicUis +
                    oldItems.slice(iofChannel + 1 until oldItems.size)

        _state.value = _state.value.copy(items = newItems)
    }

    private fun hideChatsOfChannel(streamUi: StreamUi) {
        val items = state.value.items
        val iofChannelUi = items.indexOf(streamUi)
        var iofNextChannelUi = iofChannelUi + 1
        while (items[iofNextChannelUi] !is StreamUi) {
            iofNextChannelUi++
        }

        val newItems = items.slice(0..iofChannelUi) + items.slice(iofNextChannelUi..items.lastIndex)

        _state.value = _state.value.copy(items = newItems)
    }

    fun clickChat(topicUi: TopicUi) {
        router.navigateTo(screens.topic(topicUi.streamId, topicUi.name))
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
        return matchStreams(searchQuery, items)
    }

    private suspend fun getAllStreams() = withContext(Dispatchers.Default) {
        getAllStreams.execute().map(::toChannelUi)
    }

    private suspend fun getSubscribedStreams() = withContext(Dispatchers.Default) {
        getSubscribedStreams.execute().map(::toChannelUi)
    }

    private suspend fun getTopicsForStream(streamId: Int) = withContext(Dispatchers.Default) {
        getTopicsForStream.execute(streamId).map { topic ->
            toTopicUi(topic, streamId)
        }
    }

    private fun toChannelUi(
        stream: Stream
    ) = StreamUi(
        id = stream.id,
        tag = stream.name,
        isExpanded = false,
    )

    private fun toTopicUi(
        topic: Topic,
        streamId: Int
    ) = TopicUi(
        streamId = streamId,
        name = topic.name,
        messageCount = topic.messageCount,
        color = topic.color
    )

    private fun matchStreams(
        query: String,
        streams: List<StreamUi>
    ): List<ChannelsItem> = streams
        .filter { channelUi ->
            query.lowercase() in channelUi.tag.lowercase()
        }
}
