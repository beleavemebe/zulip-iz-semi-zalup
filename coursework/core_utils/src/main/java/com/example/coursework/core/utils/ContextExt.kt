package com.example.coursework.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat

/**
 * @return was the string copied or not
 */
fun Context.copyStringToClipboard(str: String): Boolean {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
        ?: return false
    clipboard.setPrimaryClip(
        ClipData.newPlainText("idk lol", str)
    )
    return true
}
