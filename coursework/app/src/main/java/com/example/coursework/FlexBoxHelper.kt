package com.example.coursework

import android.view.View
import kotlin.math.max
import kotlin.math.min

class FlexBoxHelper(
    private val maxWidth: Int,
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

        model.width = reachedWidth
        model.height = currTop + currRowHeight + verticalSpacing + paddingBottom
        model.children = children
    }
}

class FlexBoxModel(
    var width: Int = 0,
    var height: Int = 0,
    var children: List<FlexBoxChild> = emptyList()
)

class FlexBoxChild(val left: Int, val top: Int, val view: View)
