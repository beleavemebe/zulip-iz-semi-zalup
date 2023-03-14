package com.example.core.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.StateListDrawable

class HighlightOnPressBackground(
    private val roundedCornersRadius: Float = 0f,
    private val highlightedColor: Int = 0xFF3A3A3A.toInt(),
    private val idleColor: Int = 0xFF1C1C1C.toInt()
) : StateListDrawable() {
    var highlighted = false

    private val backgroundRect = RectF()
    private var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onStateChange(stateSet: IntArray): Boolean {
        invalidateSelf()
        return super.onStateChange(stateSet)
    }

    override fun draw(canvas: Canvas) {
        backgroundRect.set(dirtyBounds)
        backgroundPaint.color = if (isHighlighted()) highlightedColor else idleColor
        canvas.drawRoundRect(
            backgroundRect,
            roundedCornersRadius,
            roundedCornersRadius,
            backgroundPaint
        )
    }

    private fun isHighlighted(): Boolean {
        return highlighted || android.R.attr.state_pressed in state
    }
}
