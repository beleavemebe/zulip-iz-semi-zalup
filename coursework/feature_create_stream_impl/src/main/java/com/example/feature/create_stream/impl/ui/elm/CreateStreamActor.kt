package com.example.feature.create_stream.impl.ui.elm

import com.example.coursework.shared_streams.api.domain.model.Stream
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import com.example.feature.create_stream.impl.domain.GetIsStreamNameValid
import com.example.feature.create_stream.impl.domain.IsStreamNameValid
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

class CreateStreamActor @Inject constructor(
    private val streamsRepository: StreamsRepository,
    private val getIsStreamNameValid: GetIsStreamNameValid,
) : Actor<CreateStreamCommand, CreateStreamEvent> {
    override fun execute(command: CreateStreamCommand) =
        when (command) {
            is CreateStreamCommand.LoadOccupiedStreamNames -> loadOccupiedStreamNames()
            is CreateStreamCommand.CreateStream -> createStream(command)
        }

    private fun loadOccupiedStreamNames() = flow {
        val streams = streamsRepository.getAllStreams(forceRemote = true).last()
        val names = streams.map(Stream::name)
        emit(CreateStreamEvent.Internal.OccupiedStreamNamesLoaded(names))
    }

    private fun createStream(command: CreateStreamCommand.CreateStream) = flow {
        val isStreamNameValid = runCatching {
            getIsStreamNameValid.execute(command.occupiedStreamNames, command.streamName)
        }.getOrDefault(IsStreamNameValid.NAME_OCCUPIED)

        if (isStreamNameValid == IsStreamNameValid.VALID) {
            streamsRepository.createStream(command.streamName)
        }
        emit(CreateStreamEvent.Internal.StreamCreateResultObtained(isStreamNameValid))
    }
}
