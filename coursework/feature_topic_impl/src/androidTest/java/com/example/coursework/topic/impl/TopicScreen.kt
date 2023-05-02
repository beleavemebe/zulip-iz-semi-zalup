package com.example.coursework.topic.impl

import android.view.View
import com.example.coursework.topic.impl.ui.PickEmojiDialog
import com.example.feature.topic.impl.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.builders.ViewBuilder
import io.github.kakaocup.kakao.common.views.KBaseView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object TopicScreen : KScreen<TopicScreen>() {
    override val layoutId = R.layout.fragment_topic
    override val viewClass = TopicScreen::class.java

    val tvError = KTextView { withId(R.id.tvError) }

    val recycler = KRecyclerView(
        builder = { withId(R.id.rvChat) },
        itemTypeBuilder = {
            itemType { MessageItem(it) }
            itemType { DateHeaderItem(it) }
        }
    )

    class MessageItem(matcher: Matcher<View>) : KRecyclerItem<MessageItem>(matcher) {
        val fbReactions = KFlexBoxLayout(matcher) { withId(R.id.fbReactions) }

    }

    class DateHeaderItem(matcher: Matcher<View>) : KRecyclerItem<DateHeaderItem>(matcher) {
        val tvDate = KTextView(matcher) { withId(R.id.tvDate) }
    }
}

class ReactionsScreen : KScreen<ReactionsScreen>() {
    override val layoutId = R.layout.dialog_pick_emoji
    override val viewClass = PickEmojiDialog::class.java

    val fbEmojis = KFlexBoxLayout { withId(R.id.fbEmojis) }
}

class KFlexBoxLayout : KBaseView<KFlexBoxLayout> {
    constructor(function: ViewBuilder.() -> Unit) : super(function)
    constructor(parent: Matcher<View>, function: ViewBuilder.() -> Unit) : super(parent, function)
}
