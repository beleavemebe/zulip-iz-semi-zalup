package com.example.coursework.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursework.chat.data.MessageRepository
import com.example.coursework.chat.model.Message
import com.example.coursework.chat.model.Reaction
import com.example.coursework.chat.ui.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatState(emptyList()))
    val state = _state.asStateFlow()

    init {
        MessageRepository.messages
            .map(::toMessageUis)
            .map(::attachDateHeaders)
            .flowOn(Dispatchers.Default)
            .onEach { items ->
                _state.value = _state.value.copy(items = items)
            }
            .launchIn(viewModelScope)
    }

    private fun toMessageUis(messages: List<Message>): List<MessageUi> {
        return messages.map { message ->
            MessageUi(
                id = message.id,
                author = message.author,
                authorImageUrl = message.authorImageUrl,
                message = message.message,
                posted = message.posted,
                reactions = message.reactions.toReactionUis()
            )
        }
    }

    private fun List<Reaction>.toReactionUis(): List<ReactionUi> {
        val reactionsByEmote = groupBy { reaction -> reaction.emojiCode }
        return reactionsByEmote.entries
            .map { (emote, reactions) ->
                ReactionUi(
                    emote = emote,
                    reactionCount = reactions.size,
                    isPressed = MessageRepository.currentUserId in reactionsByEmote[emote]!!.map(
                        Reaction::userId
                    )
                )
            }
            .sortedByDescending(ReactionUi::reactionCount)
    }

    private fun attachDateHeaders(messageUis: List<MessageUi>): List<ChatItem> {
        val messagesByDate = messageUis.groupBy { it.posted.toLocalDate() }.toSortedMap()
        return messagesByDate.keys.map { date ->
            val dateHeader = DateHeaderUi(date)
            val messages = messagesByDate[date]!!.sortedBy(MessageUi::posted)
            buildList {
                this.add(dateHeader)
                this.addAll(messages)
            }
        }.flatten()
    }

    fun sendOrRevokeReaction(messageUi: MessageUi, pickedEmoji: String) {
        viewModelScope.launch {
            MessageRepository.sendOrRevokeReaction(messageUi.id, pickedEmoji)
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            MessageRepository.sendMessage(_state.value.inputText)
        }
        setMessageInput("")
    }

    fun setMessageInput(text: String) {
        _state.value = _state.value.copy(
            inputText = text,
            isSendButtonVisible = text.isNotBlank(),
        )
    }
}
