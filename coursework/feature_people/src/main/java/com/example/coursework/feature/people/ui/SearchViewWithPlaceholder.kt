package com.example.coursework.feature.people.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.coursework.feature.people.R

class SearchViewWithPlaceholder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
    val placeholderTextView: TextView

    var listener: SearchView.OnQueryTextListener? = null

    init {
        inflate(context, R.layout.layout_search_view_with_placeholder, this)
        placeholderTextView = findViewById(R.id.tvPlaceholder)
        val searchView = findViewById<SearchView>(R.id.searchViewIdk)
        handleQueryChange(searchView)
        handleQueryFocusChange(searchView)
        handleClosing(searchView)
    }

    private fun handleQueryChange(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return listener?.onQueryTextSubmit(query) ?: false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                placeholderTextView.isVisible = false
                return listener?.onQueryTextChange(newText) ?: false
            }
        })
    }

    private fun handleQueryFocusChange(searchView: SearchView) {
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                placeholderTextView.isVisible = false
            }
        }
    }

    private fun handleClosing(searchView: SearchView) {
        searchView.setOnCloseListener {
            placeholderTextView.isVisible = true
            false
        }
    }
}
