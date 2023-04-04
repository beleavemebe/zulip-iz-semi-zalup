package com.example.coursework.chat.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.core.ui.FlexBoxLayout
import com.example.core.ui.dp
import com.example.core.ui.uiSensitiveProperty
import com.example.coursework.chat.ui.model.ReactionUi
import com.example.feature_chat.R

class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val ivRightMargin = dp(9f).toInt()
    private var tvMessageAuthor: TextView
    private var tvMessageContent: TextView
    private var ivMessageAuthorPic: ImageView
    private var fbReactions: FlexBoxLayout

    var author: String by uiSensitiveProperty("", ::onAuthorUpdate)
    var message: String by uiSensitiveProperty("", ::onMessageContentUpdate)
    var messageReactions: List<ReactionUi> by uiSensitiveProperty(emptyList(), ::onReactionsUpdated)

    init {
        isClickable = true
        inflate(context, R.layout.layout_message, this)
        tvMessageAuthor = findViewById(R.id.tvMessageAuthor)
        tvMessageContent = findViewById(R.id.tvMessageContent)
        ivMessageAuthorPic = findViewById(R.id.ivMessageAuthorPic)
        fbReactions = findViewById(R.id.fbReactions)
    }

    private fun onAuthorUpdate(value: String) {
        tvMessageAuthor.text = value
    }

    private fun onMessageContentUpdate(value: String) {
        tvMessageContent.text = value
    }

    private fun onReactionsUpdated(
        messageReactions: List<ReactionUi>,
    ) {
        fbReactions.removeAllViews()
        if (messageReactions.isNotEmpty()) {
            messageReactions.forEach { reactionUi ->
                fbReactions.addView(
                    EmoteReactionView(context).apply {
                        setData(
                            pressed = reactionUi.isPressed,
                            emote = reactionUi.emote,
                            emoteName = reactionUi.name,
                            reactions = reactionUi.reactionCount.toString()
                        )
                    }
                )
            }
            fbReactions.addView(
                AddReactionView(context)
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val width = View.getDefaultSize(calcWidth(), widthMeasureSpec)
        val height = calcHeight()
        setMeasuredDimension(width, height)
    }

    private fun calcHeight(): Int {
        return paddingTop +
                tvMessageAuthor.measuredHeight +
                tvMessageContent.measuredHeight +
                fbReactions.measuredHeight +
                paddingBottom
    }

    private fun calcWidth(): Int {
        return paddingLeft +
                ivRightMargin +
                maxOf(
                    tvMessageAuthor.measuredWidth,
                    tvMessageContent.measuredWidth,
                    fbReactions.measuredWidth
                ) +
                paddingRight
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutImageView(l, t, r, b)
        layoutMessageAuthor(l, t, r, b)
        layoutMessageContent(l, t, r, b)
        layoutReactions(l, t, r, b)
    }

    private fun layoutImageView(l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft
        val top = paddingTop
        val view = ivMessageAuthorPic
        view.layout(
            left,
            top,
            left + view.measuredWidth,
            top + view.measuredHeight
        )
    }

    private fun layoutMessageAuthor(l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
        val top = paddingTop
        tvMessageAuthor.layout(
            left,
            top,
            left + tvMessageAuthor.measuredWidth - tvMessageAuthor.paddingRight,
            top + tvMessageAuthor.measuredHeight
        )
    }

    private fun layoutMessageContent(l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
        val top = paddingTop + tvMessageAuthor.measuredHeight
        tvMessageContent.layout(
            left,
            top,
            minOf(r, left + tvMessageContent.measuredWidth) - tvMessageContent.paddingRight,
            top + tvMessageContent.measuredHeight
        )
    }

    private fun layoutReactions(l: Int, t: Int, r: Int, b: Int) {
        val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
        val top = paddingTop + tvMessageAuthor.measuredHeight + tvMessageContent.measuredHeight
        fbReactions.layout(
            left,
            top,
            left + fbReactions.measuredWidth - fbReactions.paddingRight,
            top + fbReactions.measuredHeight
        )
    }
}
