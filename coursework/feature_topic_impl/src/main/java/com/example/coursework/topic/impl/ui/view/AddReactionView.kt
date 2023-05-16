package com.example.coursework.topic.impl.ui.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding
import com.example.core.ui.HighlightOnPressBackground
import com.example.core.ui.dp
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.backgroundCornerRadius
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.reactionViewHeight
import com.example.coursework.topic.impl.ui.view.ReactionViewDefaults.reactionViewWidth
import com.example.feature.topic.impl.R

class AddReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    private val width = reactionViewWidth
    private val height = reactionViewHeight
    private val background = HighlightOnPressBackground(backgroundCornerRadius)

    private val imageView = ImageView(context, attrs, defStyleAttr, defStyleRes).apply {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_add)!!
        val color = context.getColor(com.example.core_ui.R.color.tint_primary)
        drawable.setTint(color)
        setImageDrawable(drawable)
        contentDescription = context.getString(R.string.add_new_reaction)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setPadding(dp(9f).toInt())
    }

    init {
        addView(imageView)
        setBackground(background)
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        imageView.draw(canvas)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        imageView.layout(l, t, r, b)
    }
}
