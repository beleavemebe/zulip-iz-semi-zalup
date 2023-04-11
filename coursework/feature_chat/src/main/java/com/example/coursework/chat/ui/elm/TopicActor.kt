package com.example.coursework.chat.ui.elm

import com.example.coursework.chat.domain.MessageRepository
import com.example.coursework.chat.domain.model.Message
import com.example.coursework.chat.domain.model.Reaction
import com.example.coursework.chat.ui.model.DateHeaderUi
import com.example.coursework.chat.ui.model.MessageUi
import com.example.coursework.chat.ui.model.ReactionUi
import com.example.coursework.chat.ui.model.TopicItem
import com.example.coursework.core.utils.CacheContainer
import com.example.coursework.core.utils.cache
import com.example.coursework.shared.profile.domain.usecase.GetCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import vivid.money.elmslie.coroutines.Actor

class TopicActor(
    private val messageRepository: MessageRepository,
    private val getCurrentUser: GetCurrentUser,
) : Actor<TopicCommand, TopicEvent>, CacheContainer by CacheContainer.Map() {
    override fun execute(command: TopicCommand) =
        when (command) {
            is TopicCommand.LoadAllMessages -> loadAllMessages(command)
            is TopicCommand.SendReaction -> sendReaction(command)
            is TopicCommand.RevokeReaction -> revokeReaction(command)
            is TopicCommand.SendMessage -> sendMessage(command)
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = TopicEvent.Internal::CaughtError
        )

    private fun loadAllMessages(command: TopicCommand.LoadAllMessages) = flow {
        val messages = loadMessages(command.stream, command.topic)
        this.emit(TopicEvent.Internal.MessagesLoaded(messages))
    }.flowOn(Dispatchers.Default)

    private fun sendReaction(command: TopicCommand.SendReaction) = flow {
        messageRepository.sendReaction(command.id, command.emoteName)
        val messages = loadMessages(command.stream, command.topic)
        emit(TopicEvent.Internal.MessagesLoaded(messages))
    }

    private fun revokeReaction(command: TopicCommand.RevokeReaction) = flow {
        messageRepository.revokeReaction(command.id, command.emoteName)
        val messages = loadMessages(command.stream, command.topic)
        emit(TopicEvent.Internal.MessagesLoaded(messages))
    }

    private fun sendMessage(command: TopicCommand.SendMessage) = flow {
        messageRepository.sendMessage(command.stream, command.topic, command.message)
        val messages = loadMessages(command.stream, command.topic)
        emit(TopicEvent.Internal.MessagesLoaded(messages))
    }

    private suspend fun loadMessages(
        stream: Int,
        topic: String,
    ): List<TopicItem> {
        return messageRepository
            .loadMessages(stream, topic)
            .toMessageUis()
            .attachDateHeaders()
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
        return reactions.any { reaction -> reaction.userId == getCurrentUser().id }
    }

    private suspend fun getCurrentUser() = cache("getCurrentUser") { getCurrentUser.execute() }

    private fun List<MessageUi>.attachDateHeaders(): List<TopicItem> {
        val messagesByDate = groupBy { msg -> msg.posted.toLocalDate() }.toSortedMap()
        return messagesByDate.keys.map { date ->
            val dateHeader = DateHeaderUi(date)
            val messages = messagesByDate[date]!!.sortedBy(MessageUi::posted)
            buildList {
                this.add(dateHeader)
                this.addAll(messages)
            }
        }.flatten()
    }

}
