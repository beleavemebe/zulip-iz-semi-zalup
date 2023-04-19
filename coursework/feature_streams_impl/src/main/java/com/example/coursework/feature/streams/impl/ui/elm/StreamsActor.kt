package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.domain.model.Stream
import com.example.coursework.feature.streams.impl.domain.model.Topic
import com.example.coursework.feature.streams.impl.domain.usecase.GetAllStreams
import com.example.coursework.feature.streams.impl.domain.usecase.GetSubscribedStreams
import com.example.coursework.feature.streams.impl.domain.usecase.GetTopicsForStream
import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab
import com.example.coursework.feature.streams.impl.ui.model.TopicUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@StreamsScope
class StreamsActor @Inject constructor(
    private val getAllStreams: GetAllStreams,
    private val getSubscribedStreams: GetSubscribedStreams,
    private val getTopicsForStream: GetTopicsForStream,
) : Actor<StreamsCommand, StreamsEvent> {
    override fun execute(command: StreamsCommand) =
        when (command) {
            is StreamsCommand.LoadStreams -> loadStreams(command)
            is StreamsCommand.LoadTopics -> loadTopics(command)
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = { throwable -> StreamsEvent.Internal.CaughtError(throwable) }
        )

    private fun loadStreams(command: StreamsCommand.LoadStreams) = flow {
        val streams = loadItems(command.query, command.tab)
        emit(StreamsEvent.Internal.StreamsLoaded(streams))
    }

    private suspend fun loadItems(
        searchQuery: String?,
        tab: StreamsTab,
    ): List<StreamUi> {
        val items = when (tab) {
            StreamsTab.ALL -> getAllStreams()
            StreamsTab.SUBSCRIBED -> getSubscribedStreams()
        }
        return if (searchQuery.isNullOrBlank()) {
            items
        } else {
            matchStreams(searchQuery, items)
        }
    }

    private suspend fun getAllStreams() = withContext(Dispatchers.Default) {
        getAllStreams.execute().map(::toStreamUi)
    }

    private suspend fun getSubscribedStreams() = withContext(Dispatchers.Default) {
        getSubscribedStreams.execute().map(::toStreamUi)
    }

    private fun toStreamUi(
        stream: Stream,
    ) = StreamUi(
        id = stream.id,
        tag = stream.name,
        isExpanded = false,
    )

    private fun matchStreams(
        query: String,
        streams: List<StreamUi>,
    ): List<StreamUi> = streams.filter { streamUi ->
        query.lowercase() in streamUi.tag.lowercase()
    }

    private fun loadTopics(command: StreamsCommand.LoadTopics) = flow {
        val topics = loadTopics(command.streamUi.id)
        emit(StreamsEvent.Internal.TopicsLoaded(command.streamUi, topics))
    }

    private suspend fun loadTopics(streamId: Int) = withContext(Dispatchers.Default) {
        getTopicsForStream.execute(streamId).map { topic ->
            toTopicUi(topic, streamId)
        }
    }

    private fun toTopicUi(
        topic: Topic,
        streamId: Int,
    ) = TopicUi(
        streamId = streamId,
        name = topic.name,
        messageCount = topic.messageCount,
        color = topic.color
    )
}
