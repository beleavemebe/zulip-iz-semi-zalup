package com.example.coursework.topic.impl.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.topic.impl.ui.PagingTriggeringScrollListener.Companion.DEBOUNCE_TIMEOUT
import com.example.coursework.topic.impl.ui.PagingTriggeringScrollListener.Companion.TRIGGER_LOAD_PAGE_THRESHOLD

/**
 * Dispatches a paging request each time the furthest visible item, oldest or newest
 * is within [TRIGGER_LOAD_PAGE_THRESHOLD] of the last one respectively.
 * Dispatching is debounced by [DEBOUNCE_TIMEOUT] millis.
 */
class PagingTriggeringScrollListener(
    private val layoutManager: LinearLayoutManager,
    private inline val dispatchLoadPreviousPage: () -> Unit,
    private inline val dispatchLoadNextPage: () -> Unit,
) : RecyclerView.OnScrollListener() {
    private var nearestDispatch: Long = 0L

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItems = layoutManager.itemCount
        val oldestVisible = layoutManager.findFirstVisibleItemPosition()
        val newestVisible = layoutManager.findLastVisibleItemPosition()
        val isScrollingForward = dy > 0
        when {
            !isScrollingForward && oldestVisible - TRIGGER_LOAD_PAGE_THRESHOLD < 0 -> {
                if (canDispatch()) dispatchLoadPreviousPage()
            }
            isScrollingForward && newestVisible + TRIGGER_LOAD_PAGE_THRESHOLD > totalItems -> {
                if (canDispatch()) dispatchLoadNextPage()
            }
        }
    }

    private fun canDispatch(): Boolean {
        val currMillis = System.currentTimeMillis()
        return if (currMillis > nearestDispatch) {
            nearestDispatch = currMillis + DEBOUNCE_TIMEOUT
            true
        } else {
            false
        }
    }

    companion object {
        private const val TRIGGER_LOAD_PAGE_THRESHOLD = 5
        private const val DEBOUNCE_TIMEOUT = 500L
    }
}
