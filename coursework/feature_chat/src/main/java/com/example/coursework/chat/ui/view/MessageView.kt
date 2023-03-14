package com.example.coursework.chat.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
    var authorImgUrl: String by uiSensitiveProperty("", ::onAuthorImgUrlUpdate)
    var message: String by uiSensitiveProperty("", ::onMessageContentUpdate)
    var reactions: List<Reaction> by uiSensitiveProperty(emptyList(), ::onReactionsUpdated)

    init {
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

    private fun onAuthorImgUrlUpdate(value: String) {
        // load new image via glide
    }

    private fun onReactionsUpdated(
        reactions: List<Reaction>,
    ) {
        fbReactions.removeAllViews()
        reactions.forEach {
            fbReactions.addView(
                EmoteReactionView(context).apply {
                    setData(pressed = it.isPressed, emote = it.emote, reactions = it.reactions.toString())
                }
            )
        }
        fbReactions.addView(
            EmoteReactionView(context).apply {
                setData(pressed = false, emote = "+", reactions = "")
            }
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val width = calcWidth()
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
        return maxOf(context.screenWidth, paddingLeft +
                ivRightMargin +
                maxOf(
                    tvMessageAuthor.measuredWidth,
                    tvMessageContent.measuredWidth,
                    fbReactions.measuredWidth
                ) +
                paddingRight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        with(ivMessageAuthorPic) {
            val left = paddingLeft
            val top = paddingTop
            layout(
                left,
                top,
                left + measuredWidth,
                top + measuredHeight
            )
        }

        with(tvMessageAuthor) {
            val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
            val top = paddingTop
            layout(
                left,
                top,
                left + measuredWidth,
                top + measuredHeight
            )
        }

        with(tvMessageContent) {
            val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
            val top = paddingTop + tvMessageAuthor.measuredHeight
            layout(
                left,
                top,
                left + measuredWidth,
                top + measuredHeight - paddingRight
            )
        }

        with(fbReactions) {
            val left = paddingLeft + ivMessageAuthorPic.measuredWidth + ivRightMargin
            val top = paddingTop + tvMessageAuthor.measuredHeight + tvMessageContent.measuredHeight
            layout(
                left,
                top,
                left + measuredWidth,
                top + measuredHeight - paddingRight
            )
        }
    }
}
