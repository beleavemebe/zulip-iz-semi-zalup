package com.example.core.ui

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.SearchView

val View.screenWidth: Int
    get() = context.resources.displayMetrics.widthPixels

fun View.sp(value: Float) = applyDimension(TypedValue.COMPLEX_UNIT_SP, value)

fun View.dp(value: Float) = applyDimension(TypedValue.COMPLEX_UNIT_DIP, value)

private fun View.applyDimension(unit: Int, value: Float) =
    TypedValue.applyDimension(unit, value, context.resources.displayMetrics)

fun SearchView.doOnQueryChanged(onQueryChange: (String) -> Unit) {
    setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                onQueryChange(newText)
                return true
            }
        }
    )
}

val View.leftMargin: Int
    get() = (layoutParams as MarginLayoutParams).leftMargin

val View.rightMargin: Int
    get() = (layoutParams as MarginLayoutParams).rightMargin

val View.topMargin: Int
    get() = (layoutParams as MarginLayoutParams).topMargin

val View.bottomMargin: Int
    get() = (layoutParams as MarginLayoutParams).bottomMargin
