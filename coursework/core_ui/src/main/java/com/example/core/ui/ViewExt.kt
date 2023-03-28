package com.example.core.ui

import android.util.TypedValue
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val View.screenWidth: Int
    get() = context.resources.displayMetrics.widthPixels

fun View.sp(value: Float) = applyDimension(TypedValue.COMPLEX_UNIT_SP, value)

fun View.dp(value: Float) = applyDimension(TypedValue.COMPLEX_UNIT_DIP, value)

private fun View.applyDimension(unit: Int, value: Float) =
    TypedValue.applyDimension(unit, value, context.resources.displayMetrics)

fun <T> uiSensitiveProperty(
    initial: T,
    onChange: (new: T) -> Unit = {}
): ReadWriteProperty<View, T> {
    return object : ReadWriteProperty<View, T> {
        private var value = initial

        override fun getValue(thisRef: View, property: KProperty<*>) = this.value

        override fun setValue(thisRef: View, property: KProperty<*>, value: T) {
            this.value = value
            onChange(value)
            thisRef.invalidate()
            thisRef.requestLayout()
        }
    }
}

