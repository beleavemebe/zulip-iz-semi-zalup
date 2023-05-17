package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.core.utils.CacheContainer
import com.example.coursework.core.utils.cache
import com.example.coursework.shared_messages.api.domain.MessageRepository
import com.example.coursework.shared_messages.api.domain.model.Message
import com.example.coursework.shared_messages.api.domain.model.Reaction
import com.example.coursework.topic.impl.di.TopicScope
import com.example.coursework.topic.impl.ui.model.ForeignMessageUi
import com.example.coursework.topic.impl.ui.model.OwnMessageUi
import com.example.coursework.topic.impl.ui.model.ReactionUi
import com.example.shared.profile.api.domain.usecase.GetCurrentUser
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@TopicScope
class TopicActor @Inject constructor(
    private val messageRepository: MessageRepository,
    private val getCurrentUser: GetCurrentUser,
) : Actor<TopicCommand, TopicEvent>, CacheContainer by CacheContainer.Map() {
    override fun execute(command: TopicCommand) =
        when (command) {
            is TopicCommand.LoadNewestMessages -> loadNewestMessages(command)
            is TopicCommand.SendReaction -> sendReaction(command)
            is TopicCommand.RevokeReaction -> revokeReaction(command)
            is TopicCommand.SendMessage -> sendMessage(command)
            is TopicCommand.LoadPreviousPage -> loadPreviousPage(command)
            is TopicCommand.LoadNextPage -> loadNextPage(command)
        }.mapEvents(
            eventMapper = { event -> event },
            errorMapper = TopicEvent.Internal::CaughtError
        )

    private fun loadNewestMessages(
        command: TopicCommand.LoadNewestMessages,
        fetchCacheFirst: Boolean = true
    ) = flow {
        emitAll(
            messageRepository.loadNewestMessages(
                stream = command.stream,
                topic = command.topic,
                forceRemote = fetchCacheFirst.not()
            ).map { result ->
                TopicEvent.Internal.MessagesLoaded(
                    hasReachedOldestMessage = result.containsOldestMessage,
                    hasReachedNewestMessage = result.containsNewestMessage,
                    messages = result.messages.toMessageUis()
                )
            }
        )
    }

    private fun sendReaction(command: TopicCommand.SendReaction) = flow<Nothing> {
        messageRepository.sendReaction(command.id, command.emoteName)
    }

    private fun revokeReaction(command: TopicCommand.RevokeReaction) = flow<Nothing> {
        messageRepository.revokeReaction(command.id, command.emoteName)
    }

    private fun sendMessage(command: TopicCommand.SendMessage) = flow {
        messageRepository.sendMessage(command.stream, command.topic, command.message)
        emitAll(
            loadNewestMessages(
                command = TopicCommand.LoadNewestMessages(command.stream, command.topic),
                fetchCacheFirst = false
            )
        )
    }

    private fun loadPreviousPage(command: TopicCommand.LoadPreviousPage) = flow {
        val result = messageRepository.loadPreviousPage(
            command.stream, command.topic, command.anchorMessageId
        )
        emit(
            TopicEvent.Internal.PreviousPageLoaded(
                hasReachedTheEnd = result.containsOldestMessage,
                messages = result.messages.toMessageUis()
            )
        )
    }

    private fun loadNextPage(command: TopicCommand.LoadNextPage) = flow {
        val result = messageRepository.loadNextPage(
            command.stream, command.topic, command.anchorMessageId
        )
        emit(
            TopicEvent.Internal.NextPageLoaded(
                hasReachedTheEnd = result.containsNewestMessage,
                messages = result.messages.toMessageUis()
            )
        )
    }

    private suspend fun List<Message>.toMessageUis() = map { message ->
        // FIXME правильно, конечно, просто дедлайн слишком близок чтоб менять сущность domain. my bad
        if (message.author == getCurrentUser().name) {
            OwnMessageUi(
                id = message.id,
                message = message.message,
                posted = message.posted,
                reactions = message.reactions.toReactionUis()
            )
        } else {
            ForeignMessageUi(
                id = message.id,
                message = message.message,
                posted = message.posted,
                reactions = message.reactions.toReactionUis(),
                author = message.author,
                authorImageUrl = message.authorImageUrl
            )
        }
    }

    private suspend fun List<Reaction>.toReactionUis(): List<ReactionUi> {
        val reactionsByEmote = groupBy { reaction -> reaction.emojiCode to reaction.emojiName }
        return reactionsByEmote.entries
            .map { (emoteCodeAndName, reactions) ->
                ReactionUi(
                    emote = String(Character.toChars(emoteCodeAndName.first.toIntOrNull(16) ?: FALLBACK_EMOJI)),
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

    companion object {
        private val FALLBACK_EMOJI = "1f480".toInt(16)
    }
}
