package com.example.coursework.chat.ui.elm

import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.chat.ui.model.TopicItem
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import kotlin.properties.Delegates

class TopicReducer : DslReducer<TopicEvent, TopicState, TopicEffect, TopicCommand>() {
    private var stream: Int by Delegates.notNull()
    private var topic: String by Delegates.notNull()

    override fun Result.reduce(event: TopicEvent) =
        when (event) {
            is TopicEvent.Ui.Init -> init(event.stream, event.topic)
            is TopicEvent.Ui.ToggleReaction -> sendOrRevokeReaction(event.messageUi, event.emoteName)
            is TopicEvent.Ui.ClickSendMessage -> sendMessage()
            is TopicEvent.Ui.UpdateInputText -> updateInput(event.value)
            is TopicEvent.Internal.MessagesLoaded -> showMessages(event.messages)
            is TopicEvent.Internal.CaughtError -> showError(event.error)
        }

    private fun Result.init(stream: Int, topic: String) {
        this@TopicReducer.stream = stream
        this@TopicReducer.topic = topic
        commands {
            +TopicCommand.LoadAllMessages(stream, topic)
        }
    }

    private fun Result.sendOrRevokeReaction(messageUi: MessageUi, emoteName: String) {
        val targetReaction = messageUi.reactions.firstOrNull { reaction ->
            reaction.name == emoteName
        }
        commands {
            if (targetReaction?.isPressed == true) {
                +TopicCommand.RevokeReaction(stream, topic, messageUi.id, emoteName)
            } else {
                +TopicCommand.SendReaction(stream, topic, messageUi.id, emoteName)
            }
        }
    }

    private fun Result.sendMessage() {
        commands {
            +TopicCommand.SendMessage(stream, topic, state.inputText)
        }
        updateInput("")
    }

    private fun Result.updateInput(value: String) {
        state {
            copy(
                inputText = value,
                isSendButtonVisible = value.isNotBlank(),
            )
        }
    }

    private fun Result.showMessages(messages: List<TopicItem>) {
        state {
            copy(
                items = messages,
                isLoading = false,
                error = null
            )
        }
    }

    private fun Result.showError(error: Throwable) {
        state {
            copy(error = error)
        }
    }
}
