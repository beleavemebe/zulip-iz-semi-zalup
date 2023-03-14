package com.example.coursework.chat.ui

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class EmoteReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var reactionEmote = ""
    private var reactionCount = ""
    private var pressed = false

    private val padding = dp(9f)

    private val textRect = Rect()
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = sp(14f)
        color = Color.WHITE
    }

    private val backgroundCornerRadius = dp(10f)
    private val background = HighlightOnPressBackground(backgroundCornerRadius)

    init {
        setBackground(background)
        isClickable = true
    }

    fun setData(
        pressed: Boolean,
        emote: String,
        reactions: String
    ) {
        this.pressed = pressed
        this.background.highlighted = pressed
        this.reactionEmote = emote
        this.reactionCount = reactions
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val text = getDisplayedText()
        textPaint.getTextBounds(text, 0, text.length, textRect)
        val width = textRect.width() + (2 * padding).toInt()
        val height = textRect.height() + (2 * padding).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawText(canvas)
    }

    private fun drawText(canvas: Canvas) {
        val text = getDisplayedText()
        val x = padding
        val y = height / 2 - textRect.exactCenterY()
        canvas.drawText(text, x, y, textPaint)
    }

    private fun getDisplayedText() = "$reactionEmote $reactionCount"

}
