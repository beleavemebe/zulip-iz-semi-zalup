package com.example.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.core_ui.R
import kotlin.math.max
import kotlin.math.min


class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private var model = FlexBoxModel()
    private val helper: FlexBoxHelper

    init {
        isClickable = true
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FlexBoxLayout, 0, 0)
        helper = FlexBoxHelper(
            maxWidth = screenWidth,
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
        helper.maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        helper.updateModel(model, childCount, ::getChildAt)
        setMeasuredDimension(resolveSize(model.width, widthMeasureSpec), model.height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        model.children.forEach { child ->
            with(child) {
                view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
            }
        }
    }
}

internal class FlexBoxModel(
    var width: Int = 0,
    var height: Int = 0,
    var children: List<FlexBoxChild> = emptyList()
)

internal class FlexBoxChild(val left: Int, val top: Int, val view: View)

internal class FlexBoxHelper(
    var maxWidth: Int,
    private val verticalSpacing: Int,
    private val horizontalSpacing: Int,
    private val paddingLeft: Int,
    private val paddingTop: Int,
    private val paddingRight: Int,
    private val paddingBottom: Int
) {
    fun updateModel(
        model: FlexBoxModel,
        childCount: Int,
        viewAt: (Int) -> View
    ) {
        val children = ArrayList<FlexBoxChild>(childCount)
        var i = 0
        var reachedWidth = paddingLeft
        var currLeft = paddingLeft
        var currTop = paddingTop
        var currRowHeight = paddingTop
        while (i != childCount) {
            val next = viewAt(i)
            val nextWidth = next.measuredWidth
            val nextHeight = next.measuredHeight

            if (currLeft + nextWidth + horizontalSpacing + paddingRight <= maxWidth) {
                currRowHeight = max(currRowHeight, nextHeight)
                children += FlexBoxChild(left = currLeft, top = currTop, view = next)
                currLeft += nextWidth + horizontalSpacing
                i++
                if (reachedWidth != maxWidth) {
                    reachedWidth = min(maxWidth, currLeft)
                }
            } else {
                reachedWidth = maxWidth
                currTop += currRowHeight + verticalSpacing
                currLeft = 0
            }
        }

        model.width = reachedWidth - horizontalSpacing
        model.height = currTop + currRowHeight + paddingBottom
        model.children = children
    }
}
