package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.ui.model.*
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
            is StreamsEvent.Ui.ClickCreateStream -> createStream()
            is StreamsEvent.Ui.ClickCreateTopic -> createTopic()
            is StreamsEvent.Ui.ClickViewAllMessages -> viewAllMessages()
            is StreamsEvent.Internal.StreamsLoaded -> showStreams(event)
            is StreamsEvent.Internal.TopicsLoaded -> showTopics(event)
            is StreamsEvent.Internal.StreamCreated -> onStreamCreated(event)
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
            showStreamContent(event.streamUi, emptyList())
        } else {
            showStreamContent(event.streamUi, listOf(TopicShimmerUi, TopicShimmerUi, TopicShimmerUi))
            commands {
                +StreamsCommand.LoadTopics(event.streamUi)
            }
        }
    }

    private fun Result.showStreamContent(streamUi: StreamUi, content: List<StreamsItem>) {
        val oldItems = state.items
        val iofStream = oldItems.indexOfFirst { item ->
            item is StreamUi && item.id == streamUi.id
        }

        var iofNextStream = iofStream + 1
        while (oldItems.getOrNull(iofNextStream) !is StreamUi) {
            if (oldItems.getOrNull(iofNextStream) == null) break
            iofNextStream++
        }

        val itemsBeforeStream = oldItems.slice(0 until iofStream)
        val streamAndContent = listOf(streamUi.copy(isExpanded = content.isNotEmpty())) + content
        val itemsAfterStream = oldItems.slice(iofNextStream until oldItems.size)

        state {
            copy(items = itemsBeforeStream + streamAndContent + itemsAfterStream)
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
        showStreamContent(event.streamUi, event.topicUis + ViewAllMessagesUi + CreateTopicUi)
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

    private fun Result.createStream() {
        deps.globalCicerone.router.navigateTo(deps.getCreateStreamScreen())
    }

    private fun Result.viewAllMessages() {
        // todo put in a separate module
    }

    private fun Result.createTopic() {
        // todo put in a separate module
        deps.globalCicerone.router.navigateTo()
    }

    private fun Result.onStreamCreated(event: StreamsEvent.Internal.StreamCreated) {
        commands {
            +StreamsCommand.LoadStreams(state.streamsTab, state.query, forceRemote = true)
        }

        effects {
            +StreamsEffect.ShowStreamCreatedToast(event.name)
        }
    }
}
