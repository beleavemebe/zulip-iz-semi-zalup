package com.example.coursework.topic.impl.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.VisibleForTesting
import com.example.core.ui.HighlightOnPressBackground
import com.example.core.ui.sp
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.backgroundCornerRadius
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.reactionViewHeight
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.reactionViewWidth


class EmoteReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private val width = reactionViewWidth
    private val height = reactionViewHeight
    private val background = HighlightOnPressBackground(backgroundCornerRadius)

    var reactionEmote = ""
        private set
    var emoteName = ""
        private set

    private var reactionCount = ""
    @VisibleForTesting
    var pressed = false
        private set

    private val textRect = Rect()
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = sp(14f)
        color = Color.WHITE
    }

    init {
        setBackground(background)
        isClickable = true
    }

    fun setData(
        pressed: Boolean,
        emote: String,
        emoteName: String,
        reactions: String,
    ) {
        this.pressed = pressed
        this.background.highlighted = pressed
        this.reactionEmote = emote
        this.emoteName = emoteName
        this.reactionCount = reactions
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val text = getDisplayedText()
        textPaint.getTextBounds(text, 0, text.length, textRect)
        val x = width / 2f - textRect.width() / 2
        val y = height / 2f + textRect.height() / 4
        canvas.drawText(text, x, y, textPaint)
    }

    private fun getDisplayedText() = "$reactionEmote $reactionCount"

}
