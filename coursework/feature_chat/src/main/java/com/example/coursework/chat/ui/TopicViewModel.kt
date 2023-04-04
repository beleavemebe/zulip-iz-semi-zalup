package com.example.coursework.chat.ui

import com.example.core.ui.base.BaseViewModel
import com.example.coursework.chat.data.MessageRepositoryImpl
import com.example.coursework.chat.model.Message
import com.example.coursework.chat.model.Reaction
import com.example.coursework.chat.ui.model.*
import com.example.coursework.core.utils.cache
import com.example.coursework.shared.profile.data.UsersRepositoryImpl
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TopicViewModel(
    private val stream: Int,
    private val topic: String
) : BaseViewModel() {
    private val messageRepository = MessageRepositoryImpl.instance
    private val getCurrentUser = GetCurrentUser(UsersRepositoryImpl.instance)

    private val _state = MutableStateFlow(TopicState())
    val state = _state.asStateFlow()

    init {
        loadMessages()
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        _state.value = _state.value.copy(error = throwable)
    }

    private fun loadMessages() {
        coroutineScope.launch(Dispatchers.Default) {
            val messages = messageRepository.loadMessages(stream, topic)
            val items = messages.toMessageUis().attachDateHeaders()
            _state.value = _state.value.copy(items = items, isLoading = false, error = null)
        }
    }

    private suspend fun List<Message>.toMessageUis() = map { message ->
        MessageUi(
            id = message.id,
            author = message.author,
            authorImageUrl = message.authorImageUrl,
            message = message.message,
            posted = message.posted,
            reactions = message.reactions.toReactionUis()
        )
    }

    private suspend fun List<Reaction>.toReactionUis(): List<ReactionUi> {
        val reactionsByEmote = groupBy { reaction -> reaction.emojiCode to reaction.emojiName }
        return reactionsByEmote.entries
            .map { (emoteCodeAndName, reactions) ->
                ReactionUi(
                    emote = String(Character.toChars(emoteCodeAndName.first.toInt(16))),
                    name = emoteCodeAndName.second,
                    reactionCount = reactions.size,
                    isPressed = isReactionPressed(reactions)
                )
            }
            .sortedByDescending(ReactionUi::reactionCount)
    }

    private suspend fun isReactionPressed(reactions: List<Reaction>): Boolean {
        return reactions.any { it.userId ==  getCurrentUser().id }
    }

    private suspend fun getCurrentUser() = cache("getCurrentUser") { getCurrentUser.execute() }

    private fun List<MessageUi>.attachDateHeaders(): List<TopicItem> {
        val messagesByDate = groupBy { it.posted.toLocalDate() }.toSortedMap()
        return messagesByDate.keys.map { date ->
            val dateHeader = DateHeaderUi(date)
            val messages = messagesByDate[date]!!.sortedBy(MessageUi::posted)
            buildList {
                this.add(dateHeader)
                this.addAll(messages)
            }
        }.flatten()
    }

    fun sendOrRevokeReaction(messageUi: MessageUi, emoteName: String) {
        coroutineScope.launch {
            val targetReaction = messageUi.reactions.firstOrNull {
                it.name == emoteName
            }
            if (targetReaction?.isPressed == true) {
                messageRepository.revokeReaction(messageUi.id, emoteName)
            } else {
                messageRepository.sendReaction(messageUi.id, emoteName)
            }
            loadMessages()
        }
    }

    fun sendMessage() {
        val message = _state.value.inputText
        sendMessage(message)
        setMessageInput("")
        loadMessages()
    }

    private fun sendMessage(message: String) {
        coroutineScope.launch {
            messageRepository.sendMessage(stream, topic, message)
        }
    }

    fun setMessageInput(text: String) {
        _state.value = _state.value.copy(
            inputText = text,
            isSendButtonVisible = text.isNotBlank(),
        )
    }
}
