package com.example.coursework.topic.impl.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.core.ui.*
import com.example.coursework.topic.impl.ui.model.ReactionUi
import com.example.feature.topic.impl.R

class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val tvMessageAuthor: TextView by lazy { findViewById(R.id.tvMessageAuthor) }
    private val tvMessageContent: TextView by lazy { findViewById(R.id.tvMessageContent) }
    private val ivMessageAuthorPic: ImageView by lazy { findViewById(R.id.ivMessageAuthorPic) }
    private val fbReactions: FlexBoxLayout by lazy { findViewById(R.id.fbReactions) }

    private val messageContentBackground: Drawable by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_message_content, null)!!
    }

    var messageReactions: List<ReactionUi> by uiSensitiveProperty(emptyList(), ::onReactionsUpdated)

    init {
        isClickable = true
        setWillNotDraw(false)
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

        var totalWidthUsed = paddingLeft
        var totalHeightUsed = 0

        measureChildWithMargins(
            ivMessageAuthorPic,
            widthMeasureSpec,
            totalWidthUsed,
            heightMeasureSpec,
            totalHeightUsed
        )

        totalWidthUsed += ivMessageAuthorPic.leftMargin +
                ivMessageAuthorPic.measuredWidth +
                ivMessageAuthorPic.rightMargin

        measureChildWithMargins(
            tvMessageAuthor,
            widthMeasureSpec,
            totalWidthUsed,
            heightMeasureSpec,
            totalHeightUsed
        )

        totalHeightUsed += tvMessageAuthor.topMargin + tvMessageAuthor.measuredHeight + tvMessageContent.topMargin

        measureChildWithMargins(
            tvMessageContent,
            widthMeasureSpec,
            totalWidthUsed,
            heightMeasureSpec,
            totalHeightUsed
        )

        totalHeightUsed += tvMessageContent.measuredHeight + fbReactions.topMargin

        measureChildWithMargins(
            fbReactions,
            widthMeasureSpec,
            totalWidthUsed,
            heightMeasureSpec,
            totalHeightUsed
        )


        totalHeightUsed += fbReactions.measuredHeight + fbReactions.bottomMargin
        totalWidthUsed = maxOf(
            totalWidthUsed + tvMessageContent.measuredWidth + tvMessageContent.rightMargin,
            totalWidthUsed + fbReactions.measuredWidth,
            totalWidthUsed + tvMessageAuthor.measuredWidth
        )

        val width = resolveSize(totalWidthUsed, widthMeasureSpec)
        val height = resolveSize(totalHeightUsed, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        placeMessageContentBackground()
        layoutImageView()
        layoutMessageAuthor()
        layoutMessageContent()
        layoutReactions()
    }

    private fun placeMessageContentBackground() {
        val left = paddingLeft +
                ivMessageAuthorPic.leftMargin +
                ivMessageAuthorPic.measuredWidth +
                (ivMessageAuthorPic.rightMargin / 2)
        val top = paddingTop
        val right = left +
                (ivMessageAuthorPic.rightMargin / 2) +
                maxOf(
                    tvMessageContent.measuredWidth,
                    tvMessageAuthor.measuredWidth
                ) +
                tvMessageContent.rightMargin
        val bottom = top +
                tvMessageAuthor.topMargin +
                tvMessageAuthor.measuredHeight +
                tvMessageContent.topMargin +
                tvMessageContent.measuredHeight +
                (fbReactions.topMargin / 2)

        messageContentBackground.setBounds(left, top, right, bottom)
    }

    private fun layoutImageView() {
        val left = paddingLeft + ivMessageAuthorPic.leftMargin
        val top = paddingTop + ivMessageAuthorPic.topMargin
        val right = left + ivMessageAuthorPic.measuredWidth
        val bottom = top + ivMessageAuthorPic.measuredHeight
        ivMessageAuthorPic.layout(left, top, right, bottom)
    }

    private fun layoutMessageAuthor() {
        val left = paddingLeft +
                ivMessageAuthorPic.leftMargin +
                ivMessageAuthorPic.measuredWidth +
                ivMessageAuthorPic.rightMargin
        val top = paddingTop + tvMessageAuthor.topMargin
        val right = left + tvMessageAuthor.measuredWidth
        val bottom = top + tvMessageAuthor.measuredHeight
        tvMessageAuthor.layout(left, top, right, bottom)
    }

    private fun layoutMessageContent() {
        val left = paddingLeft +
                ivMessageAuthorPic.leftMargin +
                ivMessageAuthorPic.measuredWidth +
                ivMessageAuthorPic.rightMargin
        val top = paddingTop +
                tvMessageAuthor.topMargin +
                tvMessageAuthor.measuredHeight +
                tvMessageContent.topMargin
        val right = left + tvMessageContent.measuredWidth - paddingRight
        val bottom = top + tvMessageContent.measuredHeight
        tvMessageContent.layout(left, top, right, bottom)
    }

    private fun layoutReactions() {
        val left = paddingLeft +
                ivMessageAuthorPic.leftMargin +
                ivMessageAuthorPic.measuredWidth +
                (ivMessageAuthorPic.rightMargin / 2)
        val top = paddingTop +
                tvMessageAuthor.topMargin +
                tvMessageAuthor.measuredHeight +
                tvMessageContent.topMargin +
                tvMessageContent.measuredHeight +
                fbReactions.topMargin
        val right = left + fbReactions.measuredWidth
        val bottom = top + fbReactions.measuredHeight
        fbReactions.layout(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        messageContentBackground.draw(canvas)
    }

    override fun generateDefaultLayoutParams(): LayoutParams = MarginLayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT
    )

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams = MarginLayoutParams(context, attrs)

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams = MarginLayoutParams(p)

}
