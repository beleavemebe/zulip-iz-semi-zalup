package com.example.coursework.topic.impl.ui.recycler

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.CallSuper
import com.example.core.ui.FlexBoxLayout
import com.example.coursework.topic.impl.ui.model.MessageUi
import com.example.coursework.topic.impl.ui.model.ReactionUi
import com.example.coursework.topic.impl.ui.view.AddReactionView
import com.example.coursework.topic.impl.ui.view.EmoteReactionView
import ru.tinkoff.mobile.tech.ti_recycler.base.BaseViewHolder
import ru.tinkoff.mobile.tech.ti_recycler.clicks.TiRecyclerClickListener

abstract class BaseMessageUiViewHolder<T : MessageUi>(
    view: View,
    longClicks: TiRecyclerClickListener,
    private val reactionClickListener: TopicViewHolderFactory.ReactionsClickListener
) : BaseViewHolder<T>(view, longClicks) {
    abstract val root: View
    abstract val tvMessageContent: TextView
    abstract val fbReactions: FlexBoxLayout

    @Suppress("UNCHECKED_CAST")
    override fun bind(item: T, payload: List<Any>) {
        val reactionsPayload = payload.getOrNull(0) as? List<ReactionUi>
        if (reactionsPayload != null) {
            bindReactions(item)
        } else {
            super.bind(item, payload)
        }
    }

    @CallSuper
    override fun bind(item: T) {
        tvMessageContent.text = Html.fromHtml(item.message, Html.FROM_HTML_MODE_COMPACT)
        bindReactions(item)
    }

    private fun bindReactions(item: MessageUi) {
        val reactions = item.reactions
        val views = if (reactions.isNotEmpty()) {
            reactions.map(::toReactionView) + AddReactionView(root.context)
        } else {
            emptyList()
        }

        fbReactions.removeAllViews()
        views.onEach { view ->
            view.setOnClickListener {
                reactionClickListener.onClick(it, item)
            }
        }.forEach(fbReactions::addView)
        root.invalidate()
        root.requestLayout()
    }

    private fun toReactionView(
        reactionUi: ReactionUi
    ) = EmoteReactionView(root.context).apply {
        setData(
            pressed = reactionUi.isPressed,
            emote = reactionUi.emote,
            emoteName = reactionUi.name,
            reactions = reactionUi.reactionCount.toString()
        )
    }
}
