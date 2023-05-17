package com.example.coursework.feature.streams.impl.ui.elm

import com.example.coursework.feature.streams.impl.di.StreamsScope
import com.example.coursework.feature.streams.impl.ui.model.StreamUi
import com.example.coursework.feature.streams.impl.ui.model.StreamsTab
import com.example.coursework.feature.streams.impl.ui.model.TopicUi
import com.example.coursework.shared_streams.api.domain.model.Stream
import com.example.coursework.shared_streams.api.domain.model.Topic
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@StreamsScope
class StreamsActor @Inject constructor(
    private val streamsRepository: StreamsRepository
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
        val streams = getStreamsFlow(command.query, command.tab, command.forceRemote)
        emitAll(
            streams.map {
                StreamsEvent.Internal.StreamsLoaded(it)
            }
        )
    }

    private fun getStreamsFlow(
        searchQuery: String?,
        tab: StreamsTab,
        forceRemote: Boolean,
    ) = when (tab) {
        StreamsTab.ALL -> streamsRepository.getAllStreams(forceRemote)
        StreamsTab.SUBSCRIBED -> streamsRepository.getSubscribedStreams(forceRemote)
    }
        .map { streams -> streams.map(::toStreamUi) }
        .map { streamUis ->
            if (searchQuery.isNullOrBlank()) {
                streamUis
            } else {
                matchStreams(searchQuery, streamUis)
            }.sortedBy(StreamUi::name)
        }

    private fun toStreamUi(
        stream: Stream,
    ) = StreamUi(
        id = stream.id,
        name = stream.name,
        isExpanded = false,
    )

    private fun matchStreams(
        query: String,
        streams: List<StreamUi>,
    ): List<StreamUi> = streams.filter { streamUi ->
        query.lowercase() in streamUi.name.lowercase()
    }

    private fun loadTopics(command: StreamsCommand.LoadTopics) = flow {
        val topicsFlow = getTopicsFlow(command)
        emitAll(
            topicsFlow.map { topicUi ->
                StreamsEvent.Internal.TopicsLoaded(command.streamUi, topicUi)
            }
        )
    }

    private fun getTopicsFlow(command: StreamsCommand.LoadTopics) =
        streamsRepository.getTopics(command.streamUi.id)
            .map { topicList ->
                topicList.map { topic ->
                    toTopicUi(topic, command.streamUi.name, command.streamUi.id)
                }
            }

    private fun toTopicUi(
        topic: Topic,
        stream: String,
        streamId: Int,
    ) = TopicUi(
        streamId = streamId,
        stream = stream,
        name = topic.name,
        color = topic.color
    )
}
