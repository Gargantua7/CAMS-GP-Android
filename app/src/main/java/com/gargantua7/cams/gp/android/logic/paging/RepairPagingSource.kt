package com.gargantua7.cams.gp.android.logic.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.Repair
import com.gargantua7.cams.gp.android.logic.model.RepairSearcher
import com.gargantua7.cams.gp.android.logic.repository.RepairRepository

/**
 * @author Gargantua7
 */
class RepairPagingSource(private val searcher: RepairSearcher) :
    PagingSource<Int, Repair>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repair> {
        return try {
            val page = params.key ?: 0
            Log.d("repair paging pages", page.toString())
            val res = RepairRepository.searchRepair(page, searcher)
            val repairs = res.getOrThrow()
            Log.d("repair paging list size", repairs.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (repairs.size < 10) null else page + 1
            LoadResult.Page(repairs, prev, next)
        } catch (e: Exception) {
            Log.d("repair paging error", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repair>): Int? = null

}
