package com.example.coursework.topic.impl.ui.elm

import com.example.coursework.topic.impl.di.TopicScope
import com.example.coursework.topic.impl.ui.model.*
import com.example.coursework.topic.impl.util.emojis
import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject
import kotlin.properties.Delegates

@TopicScope
class TopicReducer @Inject constructor() :
    DslReducer<TopicEvent, TopicState, TopicEffect, TopicCommand>() {
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
            is TopicEvent.Internal.MessagesLoaded -> showMessages(event.messages)
            is TopicEvent.Internal.CaughtError -> showError(event.error)
            is TopicEvent.Internal.PreviousPageLoaded -> onPreviousPageLoaded(event)
            is TopicEvent.Internal.NextPageLoaded -> onNextPageLoaded(event)
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

    private fun Result.loadPreviousPage() = loadPage {
        state { copy(items = listOf(LoadingUi) + items) }
        commands {
            +TopicCommand.LoadPreviousPage(stream, topic, state.oldestAnchor)
        }
    }

    private fun Result.loadNextPage() = loadPage {
        state { copy(items = items + LoadingUi) }
        commands {
            +TopicCommand.LoadNextPage(stream, topic, state.newestAnchor)
        }
    }

    private inline fun Result.loadPage(
        block: Result.() -> Unit,
    ) {
        if (state.isLoadingPage) return
        state { copy(isLoadingPage = true) }
        block()
    }

    private fun Result.showMessages(messages: List<MessageUi>) {
        state {
            copy(
                items = messages.attachDateHeaders(),
                oldestAnchor = messages.first().id,
                newestAnchor = messages.last().id,
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

    private fun Result.onPreviousPageLoaded(event: TopicEvent.Internal.PreviousPageLoaded) {
        val page = event.messages.dropLast(1) // TODO investigate. wrong anchor? wrong query?
        if (page.isEmpty()) {
            onEmptyPageLoaded()
        } else {
            val items = state.items.appendPreviousPage(page)
            updateItems(items)
        }
    }

    private fun Result.onNextPageLoaded(event: TopicEvent.Internal.NextPageLoaded) {
        val page = event.messages.drop(1) // TODO investigate. wrong anchor? wrong query?
        if (page.isEmpty()) {
            onEmptyPageLoaded()
        } else {
            val items = state.items.appendNextPage(page)
            updateItems(items)
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

    private fun List<TopicItem>.appendPreviousPage(page: List<MessageUi>): List<MessageUi> =
        page.plus(this.filterIsInstance<MessageUi>())
            .run {
                val itemsToDrop = size - VISIBLE_MESSAGES_WINDOW
                if (itemsToDrop > 0) dropLast(itemsToDrop) else this
            }

    private fun List<TopicItem>.appendNextPage(page: List<MessageUi>): List<MessageUi> =
        this.filterIsInstance<MessageUi>().plus(page)
            .run {
                val itemsToDrop = size - VISIBLE_MESSAGES_WINDOW
                if (itemsToDrop > 0) drop(itemsToDrop) else this
            }

    private fun Result.updateItems(
        items: List<MessageUi>,
    ) {
        state {
            copy(
                isLoadingPage = false,
                oldestAnchor = items.first().id,
                newestAnchor = items.last().id,
                items = items.attachDateHeaders()
            )
        }
    }

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
