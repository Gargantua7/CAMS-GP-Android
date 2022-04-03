package com.gargantua7.cams.gp.android.logic.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.Event
import com.gargantua7.cams.gp.android.logic.repository.EventRepository

/**
 * @author Gargantua7
 */
class EventPagingSource: PagingSource<Int, Event>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        return try {
            val page = params.key ?: 0
            Log.d("event paging pages", page.toString())
            val res = EventRepository.getEventsList(page)
            val events = res.getOrThrow()
            Log.d("event paging list size", events.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (events.size < 10) null else page + 1
            LoadResult.Page(events, prev, next)
        } catch (e: Exception) {
            Log.d("event paging error", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? = null
}
