package com.example.coursework.feature.create_topic.impl.ui.elm

import com.example.coursework.feature.create_topic.impl.domain.CreateTopicResult
import com.example.coursework.feature.create_topic.impl.domain.GetIsTopicNameValid
import com.example.coursework.shared_messages.api.domain.MessageRepository
import com.example.coursework.shared_streams.api.domain.model.Topic
import com.example.coursework.shared_streams.api.domain.repository.StreamsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

class CreateTopicActor @Inject constructor(
    private val streamsRepository: StreamsRepository,
    private val messageRepository: MessageRepository,
    private val getIsTopicNameValid: GetIsTopicNameValid,
) : Actor<CreateTopicCommand, CreateTopicEvent> {
    override fun execute(command: CreateTopicCommand) =
        when (command) {
            is CreateTopicCommand.LoadOccupiedTopicNames -> loadOccupiedTopicNames(command)
            is CreateTopicCommand.CreateTopic -> createTopic(command)
        }

    private fun loadOccupiedTopicNames(command: CreateTopicCommand.LoadOccupiedTopicNames) = flow {
        val topics = streamsRepository.getTopics(command.streamId).last()
        val names = topics.map(Topic::name)
        emit(CreateTopicEvent.Internal.OccupiedTopicNamesLoaded(names))
    }

    private fun createTopic(command: CreateTopicCommand.CreateTopic) = flow {
        val createTopicResult = getIsTopicNameValid.execute(
                command.occupiedTopicNames,
                command.topicName,
                command.firstMessage
            )

        if (createTopicResult == CreateTopicResult.VALID) {
            messageRepository.sendMessage(command.streamId, command.topicName, command.firstMessage)
        }
        emit(CreateTopicEvent.Internal.TopicCreateResultObtained(createTopicResult))
    }
}
