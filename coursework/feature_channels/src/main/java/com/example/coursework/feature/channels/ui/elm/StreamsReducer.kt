package com.example.coursework.feature.channels.ui.elm

import com.example.coursework.core.di.ServiceLocator
import com.example.coursework.feature.channels.ui.model.StreamUi
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer

class StreamsReducer : DslReducer<StreamsEvent, StreamsState, StreamsEffect, StreamsCommand>() {
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
        var iofNextStreamUi = iofStreamUi + 1
        while (items.getOrNull(iofNextStreamUi) !is StreamUi) {
            if (items.getOrNull(iofNextStreamUi) == null) break
            iofNextStreamUi++
        }
        val newItems = items.slice(0..iofStreamUi) +
                items.slice(iofNextStreamUi..items.lastIndex)

        state {
            copy(items = newItems)
        }

        event.streamUi.isExpanded = false
    }

    private fun goToTopic(event: StreamsEvent.Ui.ClickTopic) {
        val router = ServiceLocator.globalRouter
        val screens = ServiceLocator.screens
        router.navigateTo(
            screens.topic(event.topicUi.streamId, event.topicUi.name)
        )
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
        val iofChannel = oldItems.indexOf(event.streamUi)
        val newItems =
            oldItems.slice(0 until iofChannel) +
                    event.streamUi + event.topicUis +
                    oldItems.slice(iofChannel + 1 until oldItems.size)

        state {
            copy(items = newItems)
        }

        event.streamUi.isExpanded = true
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
