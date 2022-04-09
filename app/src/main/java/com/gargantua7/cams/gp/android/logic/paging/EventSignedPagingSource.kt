package com.gargantua7.cams.gp.android.logic.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.Person
import com.gargantua7.cams.gp.android.logic.repository.EventRepository

/**
 * @author Gargantua7
 */
class EventSignedPagingSource(val id: Long) : PagingSource<Int, Person>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val page = params.key ?: 0
            Log.d("event sign person paging pages", page.toString())
            val res = EventRepository.getSignPersonList(id, page)
            val persons = res.getOrThrow()
            Log.d("event sign person paging list size", persons.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (persons.size < 10) null else page + 1
            LoadResult.Page(persons, prev, next)
        } catch (e: Exception) {
            Log.d("event sign person paging error", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? = null
}
