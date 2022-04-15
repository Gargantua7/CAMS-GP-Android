package com.gargantua7.cams.gp.android.logic.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.logic.repository.MsgRepository

/**
 * @author Gargantua7
 */
class MsgPreviewPagingSource : PagingSource<Int, LocalMsg>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalMsg> {
        return try {
            val page = params.key ?: 0
            Log.d("msg preview paging pages", page.toString())
            val res = MsgRepository.loadPersonListFromDB(page)
            val persons = ArrayList<LocalMsg>()
            res?.forEach {
                MsgRepository.loadLastMsgFormDB(it)?.let { it1 -> persons.add(it1) }
            }
            Log.d("msg preview paging list size", persons.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (persons.size < 10) null else page + 1
            LoadResult.Page(persons, prev, next)
        } catch (e: Exception) {
            Log.d("msg preview paging error", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocalMsg>): Int? = null
}
