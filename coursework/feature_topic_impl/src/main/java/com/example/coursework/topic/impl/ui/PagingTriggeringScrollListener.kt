package com.example.coursework.topic.impl.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PagingTriggeringScrollListener(
    private val layoutManager: LinearLayoutManager,
    private inline val dispatchLoadPreviousPage: () -> Unit,
    private inline val dispatchLoadNextPage: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItems = layoutManager.itemCount
        val oldestVisible = layoutManager.findFirstVisibleItemPosition()
        val newestVisible = layoutManager.findLastVisibleItemPosition()
        val isScrollingForward = dy > 0
        when {
            oldestVisible - TRIGGER_LOAD_PAGE_THRESHOLD < 0 && isScrollingForward.not() -> {
                dispatchLoadPreviousPage()
            }
            newestVisible + TRIGGER_LOAD_PAGE_THRESHOLD > totalItems && isScrollingForward -> {
                dispatchLoadNextPage()
            }
        }
    }

    companion object {
        private const val TRIGGER_LOAD_PAGE_THRESHOLD = 5
    }
}
