package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.feature.streams.api.StreamsDeps
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

@StreamsScope
class StreamsReducer @Inject constructor(
    private val deps: StreamsDeps
) : DslReducer<StreamsEvent, StreamsState, StreamsEffect, StreamsCommand>() {
    override fun Result.reduce(event: StreamsEvent) =
        when (event) {
            is StreamsEvent.Init -> init()
            is StreamsEvent.Ui.UpdateSearchQuery -> updateSearchQuery(event)
            is StreamsEvent.Ui.SelectStreamsTab -> selectStreamsTab(event)
            is StreamsEvent.Ui.ClickStream -> toggleStream(event)
            is StreamsEvent.Ui.ClickTopic -> goToTopic(event)
            is StreamsEvent.Internal.StreamsLoaded -> showStreams(event)
            is StreamsEvent.Internal.TopicsLoaded -> showTopics(event)
            is StreamsEvent.Internal.CaughtError -> showError(event)
        }

    private fun Result.init() {
        commands {
            +StreamsCommand.LoadStreams(state.streamsTab, "")
        }
    }

    private fun Result.updateSearchQuery(
        event: StreamsEvent.Ui.UpdateSearchQuery,
    ) {
        if (state.query == event.value) return
        state {
            copy(isLoading = true, error = null, query = event.value)
        }
        commands {
            +StreamsCommand.LoadStreams(state.streamsTab, event.value)
        }
    }

    private fun Result.selectStreamsTab(
        event: StreamsEvent.Ui.SelectStreamsTab,
    ) {
        state {
            copy(isLoading = true, error = null, streamsTab = event.tab)
        }
        commands {
            +StreamsCommand.LoadStreams(event.tab, state.query)
        }
    }

    private fun Result.toggleStream(
        event: StreamsEvent.Ui.ClickStream,
    ) {
        if (event.streamUi.isExpanded) {
            collapseStream(event)
        } else {
            commands {
                +StreamsCommand.LoadTopics(event.streamUi)
            }
        }
    }

    private fun Result.collapseStream(
        event: StreamsEvent.Ui.ClickStream,
    ) {
        val items = state.items
        val iofStreamUi = items.indexOf(event.streamUi)

        var iofNextStream = iofStreamUi + 1
        while (items.getOrNull(iofNextStream) !is StreamUi) {
            if (items.getOrNull(iofNextStream) == null) break
            iofNextStream++
        }

        val itemsBeforeStream = items.slice(0 until iofStreamUi)
        val itemsAfterStream = items.slice(iofNextStream..items.lastIndex)
        state {
            copy(items = itemsBeforeStream + event.streamUi.copy(isExpanded = false) + itemsAfterStream)
        }
    }

    private fun Result.goToTopic(event: StreamsEvent.Ui.ClickTopic) {
        val screen = deps.getTopicScreen(event.topicUi.streamId, event.topicUi.stream, event.topicUi.name)
        deps.globalCicerone.router.navigateTo(screen)
    }

    private fun Result.showStreams(
        event: StreamsEvent.Internal.StreamsLoaded,
    ) {
        state {
            copy(
                isLoading = false,
                items = event.streamUis,
                error = null
            )
        }
    }

    private fun Result.showTopics(
        event: StreamsEvent.Internal.TopicsLoaded,
    ) {
        val oldItems = state.items
        val iofStream = oldItems.indexOfFirst { item ->
            item is StreamUi && item.id == event.streamUi.id
        }

        var iofNextStream = iofStream + 1
        while (oldItems.getOrNull(iofNextStream) !is StreamUi) {
            if (oldItems.getOrNull(iofNextStream) == null) break
            iofNextStream++
        }

        val itemsBeforeStream = oldItems.slice(0 until iofStream)
        val expandedStreamWithTopics = listOf(event.streamUi.copy(isExpanded = true)) + event.topicUis
        val itemsAfterStream = oldItems.slice(iofNextStream until oldItems.size)

        state {
            copy(items = itemsBeforeStream + expandedStreamWithTopics + itemsAfterStream)
        }
    }

    private fun Result.showError(
        event: StreamsEvent.Internal.CaughtError,
    ) {
        state {
            copy(
                isLoading = false,
                error = event.error,
            )
        }
    }
}
