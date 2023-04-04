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

class StreamsViewModel : BaseViewModel() {
    private val repository = StreamsRepositoryImpl.instance
    private val getAllStreams = GetAllStreams(repository)
    private val getSubscribedStreams = GetSubscribedStreams(repository)
    private val getTopicsForStream = GetTopicsForStream(repository)

    private val router = ServiceLocator.globalRouter
    private val screens = ServiceLocator.screens
    private val _state = MutableStateFlow(StreamsState())

    val state = _state.asStateFlow()
    val searchQuery = MutableStateFlow<String?>(null)

    init {
        observeSearchQuery()
        loadStreams()
    }

    private fun observeSearchQuery() {
        searchQuery
            .filterNotNull()
            .debounce(250L)
            .mapLatest { query -> updateState(query, _state.value.streamsTab) }
            .flowOn(Dispatchers.Default)
            .launchIn(coroutineScope)
    }

    private fun loadStreams() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                isLoading = false,
                items = getSubscribedStreams.execute().map(::toStreamUi)
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

    fun clickStream(streamUi: StreamUi) {
        coroutineScope.launch(Dispatchers.Default) {
            if (streamUi.isExpanded.not()) {
                val topicUis = getTopicsForStream(streamUi.id)
                showTopicsForStream(streamUi, topicUis)
                streamUi.isExpanded = true
            } else {
                hideChatsOfStream(streamUi)
                streamUi.isExpanded = false
            }
        }
    }

    private fun showTopicsForStream(
        streamUi: StreamUi,
        topicUis: List<TopicUi>,
    ) {
        val oldItems = state.value.items
        val iofChannel = oldItems.indexOf(streamUi)
        val newItems: List<StreamsItem> =
            oldItems.slice(0 until iofChannel) +
                    streamUi + topicUis +
                    oldItems.slice(iofChannel + 1 until oldItems.size)

        _state.value = _state.value.copy(items = newItems)
    }

    private fun hideChatsOfStream(streamUi: StreamUi) {
        val items = state.value.items
        val iofStreamUi = items.indexOf(streamUi)
        var iofNextStreamUi = iofStreamUi + 1
        while (items.getOrNull(iofNextStreamUi) !is StreamUi) {
            if (items.getOrNull(iofNextStreamUi) == null) break
            iofNextStreamUi++
        }

        val newItems = items.slice(0..iofStreamUi) + items.slice(iofNextStreamUi..items.lastIndex)

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
        tab: StreamsTab,
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

    private suspend fun getItems(searchQuery: String?, tab: StreamsTab): List<StreamsItem> {
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
        getAllStreams.execute().map(::toStreamUi)
    }

    private suspend fun getSubscribedStreams() = withContext(Dispatchers.Default) {
        getSubscribedStreams.execute().map(::toStreamUi)
    }

    private suspend fun getTopicsForStream(streamId: Int) = withContext(Dispatchers.Default) {
        getTopicsForStream.execute(streamId).map { topic ->
            toTopicUi(topic, streamId)
        }
    }

    private fun toStreamUi(
        stream: Stream,
    ) = StreamUi(
        id = stream.id,
        tag = stream.name,
        isExpanded = false,
    )

    private fun toTopicUi(
        topic: Topic,
        streamId: Int,
    ) = TopicUi(
        streamId = streamId,
        name = topic.name,
        messageCount = topic.messageCount,
        color = topic.color
    )

    private fun matchStreams(
        query: String,
        streams: List<StreamUi>,
    ): List<StreamsItem> = streams.filter { streamUi ->
        query.lowercase() in streamUi.tag.lowercase()
    }
}
