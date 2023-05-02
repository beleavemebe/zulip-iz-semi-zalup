package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.di.TopicScope
import com.example.coursework.topic.impl.ui.model.*
import com.example.coursework.topic.impl.util.emojis
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject
import kotlin.properties.Delegates

@TopicScope
class TopicReducer @Inject constructor() : DslReducer<TopicEvent, TopicState, TopicEffect, TopicCommand>() {
    private var stream: Int by Delegates.notNull()
    private var topic: String by Delegates.notNull()

    override fun Result.reduce(event: TopicEvent) =
        when (event) {
            is TopicEvent.Ui.Init -> init(event.stream, event.topic)
            is TopicEvent.Ui.ToggleReaction -> toggleReaction(event.messageUi, event.emoteName)
            is TopicEvent.Ui.ClickSendMessage -> sendMessage()
            is TopicEvent.Ui.UpdateInputText -> updateInput(event.value)
            is TopicEvent.Ui.LoadPreviousPage -> loadPreviousPage()
            is TopicEvent.Ui.LoadNextPage -> loadNextPage()
            is TopicEvent.Internal.MessagesLoaded -> onMessagesLoaded(event)
            is TopicEvent.Internal.PreviousPageLoaded -> onPreviousPageLoaded(event)
            is TopicEvent.Internal.NextPageLoaded -> onNextPageLoaded(event)
            is TopicEvent.Internal.CaughtError -> showError(event.error)
        }

    private fun Result.init(stream: Int, topic: String) {
        this@TopicReducer.stream = stream
        this@TopicReducer.topic = topic
        commands {
            +TopicCommand.LoadNewestMessages(stream, topic)
        }
    }

    private fun Result.toggleReaction(messageUi: MessageUi, emoteName: String) {
        val targetReaction = messageUi.reactions.firstOrNull { reaction ->
            reaction.name == emoteName
        }

        val msgToUpdateIndex = state.items.indexOf(messageUi)
        if (targetReaction?.isPressed == true) {
            removeReaction(msgToUpdateIndex, targetReaction, messageUi, emoteName)
        } else {
            addReaction(msgToUpdateIndex, targetReaction, messageUi, emoteName)
        }
    }

    private fun Result.removeReaction(
        msgToUpdateIndex: Int,
        targetReaction: ReactionUi,
        messageUi: MessageUi,
        emoteName: String,
    ) {
        state {
            copy(
                items = items.modifiedAt(msgToUpdateIndex) { item ->
                    item as MessageUi
                    if (targetReaction.reactionCount == 1) {
                        item.removeReaction(targetReaction)
                    } else {
                        item.uncheckReaction(targetReaction)
                    }
                }
            )
        }

        commands {
            +TopicCommand.RevokeReaction(stream, topic, messageUi.id, emoteName)
        }
    }

    private fun MessageUi.removeReaction(targetReaction: ReactionUi) =
        copy(
            reactions = reactions - targetReaction
        )

    private fun MessageUi.uncheckReaction(targetReaction: ReactionUi) =
        copy(
            reactions = reactions.modify(targetReaction) { reactionUi ->
                reactionUi.copy(
                    reactionCount = reactionUi.reactionCount - 1,
                    isPressed = false
                )
            }
        )

    private fun Result.addReaction(
        msgToUpdateIndex: Int,
        targetReaction: ReactionUi?,
        messageUi: MessageUi,
        emoteName: String,
    ) {
        state {
            copy(
                items = items.modifiedAt(msgToUpdateIndex) { item ->
                    (item as MessageUi).copy(
                        reactions = if (targetReaction != null) {
                            item.reactions.setExistingReactionPressed(targetReaction)
                        } else {
                            item.reactions.addNewReaction(emoteName)
                        }
                    )
                }
            )
        }

        commands {
            +TopicCommand.SendReaction(stream, topic, messageUi.id, emoteName)
        }
    }

    private fun List<ReactionUi>.setExistingReactionPressed(targetReaction: ReactionUi) =
        modify(targetReaction) { reactionUi ->
            reactionUi.copy(
                reactionCount = reactionUi.reactionCount + 1,
                isPressed = true
            )
        }

    private fun List<ReactionUi>.addNewReaction(emoteName: String) =
        this + ReactionUi(
            name = emoteName,
            emote = findEmoji(emoteName).getCodeString(),
            isPressed = true
        )

    // TODO so dumb
    private fun findEmoji(emoteName: String) = emojis.first { it.name == emoteName }

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

    private fun Result.loadPreviousPage() = loadPage(unless = state.canLoadOlderMessages.not()) {
        state { copy(items = listOf(LoadingUi) + items) }
        commands {
            +TopicCommand.LoadPreviousPage(stream, topic, state.oldestAnchor)
        }
    }

    private fun Result.loadNextPage() = loadPage(unless = state.canLoadNewerMessages.not()) {
        state { copy(items = items + LoadingUi) }
        commands {
            +TopicCommand.LoadNextPage(stream, topic, state.newestAnchor)
        }
    }

    private inline fun Result.loadPage(
        unless: Boolean,
        block: Result.() -> Unit,
    ) {
        if (unless || state.isLoadingPage) return
        state { copy(isLoadingPage = true) }
        block()
    }

    private fun Result.onMessagesLoaded(event: TopicEvent.Internal.MessagesLoaded) {
        state {
            copy(
                items = event.messages.attachDateHeaders(),
                oldestAnchor = event.messages.firstOrNull()?.id ?: 0,
                newestAnchor = event.messages.lastOrNull()?.id ?: 0,
                canLoadOlderMessages = event.hasReachedOldestMessage.not(),
                canLoadNewerMessages = event.hasReachedNewestMessage.not(),
                isLoading = false,
                error = null,
            )
        }
    }

    private fun Result.showError(error: Throwable) {
        state {
            copy(error = error)
        }
    }

    private fun Result.onPreviousPageLoaded(event: TopicEvent.Internal.PreviousPageLoaded) {
        val page = event.messages.dropLast(1) // TODO investigate. wrong anchor? wrong query?
        if (page.isEmpty()) {
            onEmptyPageLoaded()
        } else {
            val hasDroppedNewerMessages: Boolean
            val messages = page.plus(state.items.filterIsInstance<MessageUi>())
                .run {
                    val itemsToDrop = size - VISIBLE_MESSAGES_WINDOW
                    val needToDropItems = itemsToDrop > 0
                    hasDroppedNewerMessages = needToDropItems
                    if (needToDropItems) dropLast(itemsToDrop) else this
                }

            state {
                state.setUpdatedMessages(messages)
                    .copy(
                        canLoadOlderMessages = event.hasReachedTheEnd.not(),
                        canLoadNewerMessages = hasDroppedNewerMessages
                    )
            }
        }
    }

    private fun Result.onNextPageLoaded(event: TopicEvent.Internal.NextPageLoaded) {
        val page = event.messages.drop(1) // TODO investigate. wrong anchor? wrong query?
        if (page.isEmpty()) {
            onEmptyPageLoaded()
        } else {
            val hasDroppedOlderMessages: Boolean
            val messages = state.items.filterIsInstance<MessageUi>().plus(page)
                .run {
                    val itemsToDrop = size - VISIBLE_MESSAGES_WINDOW
                    val needToDropItems = itemsToDrop > 0
                    hasDroppedOlderMessages = needToDropItems
                    if (itemsToDrop > 0) drop(itemsToDrop) else this
                }

            state {
                state.setUpdatedMessages(messages)
                    .copy(
                        canLoadNewerMessages = event.hasReachedTheEnd.not(),
                        canLoadOlderMessages = hasDroppedOlderMessages
                    )
            }
        }
    }

    private fun Result.onEmptyPageLoaded() {
        state {
            copy(
                isLoadingPage = false,
                items = items.filterNot { it is LoadingUi }
            )
        }
    }

    private fun TopicState.setUpdatedMessages(
        items: List<MessageUi>,
    ): TopicState = copy(
        isLoadingPage = false,
        oldestAnchor = items.first().id,
        newestAnchor = items.last().id,
        items = items.attachDateHeaders()
    )

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

    companion object {
        private const val VISIBLE_MESSAGES_WINDOW = 80
    }
}

private inline fun <E> List<E>.modifiedAt(
    index: Int,
    modify: (E) -> E
): List<E> = toMutableList().apply {
    set(index, modify(get(index)))
}


private inline fun <E> List<E>.modify(
    element: E,
    modify: (E) -> E
): List<E> = modifiedAt(indexOf(element), modify)
