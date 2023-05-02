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
import com.example.core.ui.dp
import com.example.core.ui.sp

class EmoteReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var reactionEmote = ""
        private set
    var emoteName = ""
        private set

    private var reactionCount = ""
    @VisibleForTesting
    var pressed = false
        private set

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
        emoteName: String,
        reactions: String
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
