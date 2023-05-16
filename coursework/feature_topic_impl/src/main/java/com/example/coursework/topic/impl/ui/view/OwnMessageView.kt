package com.example.coursework.topic.impl.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.core.ui.*
import com.example.feature.topic.impl.R

class OwnMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val tvMessageContent: TextView by lazy { findViewById(R.id.tvMessageContent) }
    private val fbReactions: FlexBoxLayout by lazy { findViewById(R.id.fbReactions) }

    private val messageContentBackground: Drawable by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_own_message_content, null)!!
    }

    init {
        isClickable = true
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        measureChildWithMargins(
            tvMessageContent,
            widthMeasureSpec,
            0,
            heightMeasureSpec,
            0
        )

        measureChildWithMargins(
            fbReactions,
            widthMeasureSpec,
            0,
            heightMeasureSpec,
            0
        )

        val totalWidth = maxOf(
            tvMessageContent.leftMargin + tvMessageContent.measuredWidth + tvMessageContent.rightMargin,
            tvMessageContent.leftMargin + fbReactions.measuredWidth + tvMessageContent.rightMargin
        )

        val totalHeight = tvMessageContent.topMargin +
                tvMessageContent.measuredHeight +
                fbReactions.topMargin +
                fbReactions.measuredHeight +
                fbReactions.bottomMargin

        val width = resolveSize(totalWidth, widthMeasureSpec)
        val height = resolveSize(totalHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        placeMessageContentBackground(r)
        layoutMessageContent(r)
        layoutReactions(r)
    }

    private fun placeMessageContentBackground(r: Int) {
        val right = r - (tvMessageContent.rightMargin / 2)
        val left = right - (tvMessageContent.rightMargin / 2) - tvMessageContent.measuredWidth - tvMessageContent.leftMargin
        val top = 0
        val bottom = tvMessageContent.topMargin + tvMessageContent.measuredHeight + (fbReactions.topMargin / 2)
        messageContentBackground.setBounds(left, top, right, bottom)
    }

    private fun layoutMessageContent(r: Int) {
        val right = r - tvMessageContent.rightMargin
        val left = right - tvMessageContent.measuredWidth
        val top = tvMessageContent.topMargin
        val bottom = top + tvMessageContent.measuredHeight
        tvMessageContent.layout(left, top, right, bottom)
    }

    private fun layoutReactions(r: Int) {
        val top = tvMessageContent.topMargin + tvMessageContent.measuredHeight + fbReactions.topMargin
        val bottom = top + fbReactions.measuredHeight
        val right = r - tvMessageContent.rightMargin / 2
        val left = right - fbReactions.measuredWidth
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
