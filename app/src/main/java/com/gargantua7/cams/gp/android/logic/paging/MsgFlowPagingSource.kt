package com.gargantua7.cams.gp.android.logic.paging

import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gargantua7.cams.gp.android.logic.model.LocalMsg
import com.gargantua7.cams.gp.android.logic.repository.MsgRepository

/**
 * @author Gargantua7
 */
class MsgFlowPagingSource(val id: String) : PagingSource<Int, LocalMsg>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalMsg> {
        return try {
            val page = params.key ?: 0
            Log.d("msg flow ($id) paging pages", page.toString())
            val res = MsgRepository.loadMsgFlowById(id, page) ?: throw NotFoundException()
            Log.d("msg flow ($id) paging list size", res.size.toString())
            val prev = if (page > 0) page - 1 else null
            val next = if (res.size < 10) null else page + 1
            LoadResult.Page(res, prev, next)
        } catch (e: Exception) {
            Log.d("msg flow ($id) paging error", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocalMsg>): Int? = null
}
