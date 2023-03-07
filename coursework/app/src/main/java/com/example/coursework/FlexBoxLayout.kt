package com.example.coursework

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private var model = FlexBoxModel()
    private val helper: FlexBoxHelper

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FlexBoxLayout, 0, 0)
        helper = FlexBoxHelper(
            maxWidth = context.resources.displayMetrics.widthPixels,
            verticalSpacing = attributes
                .getDimension(R.styleable.FlexBoxLayout_verticalSpacing, 0f)
                .toInt(),
            horizontalSpacing = attributes
                .getDimension(R.styleable.FlexBoxLayout_horizontalSpacing, 0f)
                .toInt(),
            paddingLeft = paddingStart,
            paddingTop = paddingTop,
            paddingRight = paddingEnd,
            paddingBottom = paddingBottom
        )
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        helper.updateModel(model, childCount, ::getChildAt)
        setMeasuredDimension(model.width, model.height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        model.children.forEach { child ->
            with(child) {
                view.layout(left, top, left + view.measuredWidth, top + view.measuredWidth)
            }
        }
    }
}

