package com.example.coursework.feature.create_topic.impl.domain

import javax.inject.Inject

class GetIsTopicNameValid @Inject constructor() {
    fun execute(occupiedTopicNames: List<String>?, topicName: String, firstMessage: String): CreateTopicResult {
        return when {
            occupiedTopicNames == null -> CreateTopicResult.WAIT_A_BIT
            topicName.isBlank() -> CreateTopicResult.NAME_BLANK
            topicName in occupiedTopicNames -> CreateTopicResult.NAME_OCCUPIED
            firstMessage.isBlank() -> CreateTopicResult.MESSAGE_BLANK
            else -> CreateTopicResult.VALID
        }
    }
}
