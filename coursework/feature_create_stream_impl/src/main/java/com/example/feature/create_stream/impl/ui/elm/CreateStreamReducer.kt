package com.example.feature.create_stream.impl.ui.elm

import com.example.coursework.feature.create_stream.api.CreateStreamDeps
import com.example.feature.create_stream.impl.domain.IsStreamNameValid
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class CreateStreamReducer @Inject constructor(
    private val deps: CreateStreamDeps
) : DslReducer<CreateStreamEvent, CreateStreamState, CreateStreamEffect, CreateStreamCommand>() {
    override fun Result.reduce(event: CreateStreamEvent) =
        when (event) {
            is CreateStreamEvent.Ui.Init -> init()
            is CreateStreamEvent.Ui.ClickGoBack -> goBack()
            is CreateStreamEvent.Ui.ClickCreateStream -> tryCreateStream()
            is CreateStreamEvent.Ui.UpdateStreamTitle -> updateStreamTitle(event)
            is CreateStreamEvent.Internal.OccupiedStreamNamesLoaded -> onOccupiedStreamNamesLoaded(event)
            is CreateStreamEvent.Internal.StreamCreateResultObtained -> onStreamCreateResultObtained(event)
        }

    private fun Result.init() {
        commands { +CreateStreamCommand.LoadOccupiedStreamNames }
    }

    private fun Result.goBack() {
        deps.globalCicerone.router.backTo(null)
    }

    private fun Result.tryCreateStream() {
        commands {
            +CreateStreamCommand.CreateStream(state.occupiedStreamNames, state.streamName)
        }
    }

    private fun Result.updateStreamTitle(event: CreateStreamEvent.Ui.UpdateStreamTitle) {
        state {
            copy(
                streamName = event.value,
                isStreamNameValid = null
            )
        }
    }

    private fun Result.onOccupiedStreamNamesLoaded(event: CreateStreamEvent.Internal.OccupiedStreamNamesLoaded) {
        state {
            copy(occupiedStreamNames = event.values)
        }
    }

    private fun Result.onStreamCreateResultObtained(event: CreateStreamEvent.Internal.StreamCreateResultObtained) {
        state {
            copy(isStreamNameValid = event.isStreamNameValid)
        }

        if (event.isStreamNameValid == IsStreamNameValid.VALID) {
            deps.onStreamCreated(state.streamName)
        }
    }
}
