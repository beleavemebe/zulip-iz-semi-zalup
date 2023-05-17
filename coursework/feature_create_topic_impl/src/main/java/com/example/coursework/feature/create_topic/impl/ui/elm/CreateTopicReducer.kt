package com.example.coursework.feature.create_topic.impl.ui.elm

import com.example.coursework.feature.create_topic.impl.domain.CreateTopicResult
import com.example.feature.create_topic.api.CreateTopicDeps
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class CreateTopicReducer @Inject constructor(
    private val deps: CreateTopicDeps
) : DslReducer<CreateTopicEvent, CreateTopicState, CreateTopicEffect, CreateTopicCommand>() {
    override fun Result.reduce(event: CreateTopicEvent) =
        when (event) {
            is CreateTopicEvent.Ui.Init -> init(event)
            is CreateTopicEvent.Ui.ClickGoBack -> goBack()
            is CreateTopicEvent.Ui.ClickCreateTopic -> tryCreateTopic()
            is CreateTopicEvent.Ui.UpdateTopicTitle -> updateTopicTitle(event)
            is CreateTopicEvent.Ui.UpdateFirstMessage -> updateFirstMessage(event)
            is CreateTopicEvent.Internal.OccupiedTopicNamesLoaded -> onOccupiedTopicNamesLoaded(event)
            is CreateTopicEvent.Internal.TopicCreateResultObtained -> onTopicCreateResultObtained(event)
        }

    private fun Result.init(event: CreateTopicEvent.Ui.Init) {
        state {
            copy(streamId = event.streamId, stream = event.stream)
        }
        commands {
            +CreateTopicCommand.LoadOccupiedTopicNames(event.streamId)
        }
    }

    private fun Result.goBack() {
        deps.globalCicerone.router.backTo(null)
    }

    private fun Result.tryCreateTopic() {
        commands {
            +CreateTopicCommand.CreateTopic(
                state.occupiedTopicNames,
                state.streamId,
                state.topicName,
                state.firstMessage
            )
        }
    }

    private fun Result.updateTopicTitle(event: CreateTopicEvent.Ui.UpdateTopicTitle) {
        state {
            copy(
                topicName = event.value,
                createTopicResult = null
            )
        }
    }

    private fun Result.updateFirstMessage(event: CreateTopicEvent.Ui.UpdateFirstMessage) {
        state {
            copy(firstMessage = event.value)
        }
    }

    private fun Result.onOccupiedTopicNamesLoaded(event: CreateTopicEvent.Internal.OccupiedTopicNamesLoaded) {
        state {
            copy(occupiedTopicNames = event.values)
        }
    }

    private fun Result.onTopicCreateResultObtained(event: CreateTopicEvent.Internal.TopicCreateResultObtained) {
        state {
            copy(createTopicResult = event.createTopicResult)
        }

        if (event.createTopicResult == CreateTopicResult.VALID) {
            deps.onTopicCreated(state.streamId, state.stream, state.topicName)
        }
    }
}
